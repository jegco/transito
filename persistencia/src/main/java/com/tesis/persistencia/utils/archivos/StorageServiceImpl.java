package com.tesis.persistencia.utils.archivos;

import com.tesis.dominio.utils.StorageService;
import com.tesis.persistencia.utils.archivos.excepciones.FileNotFoundException;
import com.tesis.persistencia.utils.archivos.excepciones.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path fileStorageLocation;

    @Autowired
    public StorageServiceImpl(FileStorageProperties fileStorageProperties) {

        try {
            this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                    .toAbsolutePath().normalize();
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    @Override
    public Mono<String> guardarDocumento(Flux<DataBuffer> archivo, String nombreOriginalArchivo) {
        final String filename = nombreOriginalArchivo;
        File file = new File(fileStorageLocation.toString()+ "/" + filename);
        if (file.exists())
            file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            return Mono.error(e); // if creating a new file fails return an error
        }

        try {
            // create an async file channel to store the file on disk
            final AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(file.toPath(), StandardOpenOption.WRITE);

            final CloseCondition closeCondition = new CloseCondition();

            // pointer to the end of file offset
            AtomicInteger fileWriteOffset = new AtomicInteger(0);
            // error signal
            AtomicBoolean errorFlag = new AtomicBoolean(false);

            // FilePart.content produces a flux of data buffers, each need to be written to the file
            return archivo.doOnEach(dataBufferSignal -> {
                if (dataBufferSignal.hasValue() && !errorFlag.get()) {
                    // read data from the incoming data buffer into a file array
                    DataBuffer dataBuffer = dataBufferSignal.get();
                    int count = dataBuffer.readableByteCount();
                    byte[] bytes = new byte[count];
                    dataBuffer.read(bytes);

                    // create a file channel compatible byte buffer
                    final ByteBuffer byteBuffer = ByteBuffer.allocate(count);
                    byteBuffer.put(bytes);
                    byteBuffer.flip();

                    // get the current write offset and increment by the buffer size
                    final int filePartOffset = fileWriteOffset.getAndAdd(count);
                    // write the buffer to disk
                    closeCondition.onTaskSubmitted();
                    fileChannel.write(byteBuffer, filePartOffset, null, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            // file part successfuly written to disk, clean up
                            byteBuffer.clear();

                            if (closeCondition.onTaskCompleted())
                                try {
                                    fileChannel.close();
                                } catch (IOException ignored) {
                                    ignored.printStackTrace();
                                }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            // there as an error while writing to disk, set an error flag
                            errorFlag.set(true);
                        }
                    });
                }
            }).doOnComplete(() -> {
                // all done, close the file channel

                if (closeCondition.canCloseOnComplete())
                    try {

                        fileChannel.close();
                    } catch (IOException ignored) {
                    }

            }).doOnError(t -> {
                // ooops there was an error

                try {
                    fileChannel.close();
                } catch (IOException ignored) {
                }
                // take last, map to a status string
            }).last().map(dataBuffer -> file.getPath() + " " + (errorFlag.get() ? "error" : ""));
        } catch (IOException e) {
            // unable to open the file channel, return an error

            return Mono.error(e);
        }
    }

    @Override
    public Mono<Resource> cargarDocumento(String nombre) {
        return Mono.just(cargar(nombre));
    }

    @Override
    public Mono<Void> eliminarArchivo(File archivo) {
        return null;
    }

    private String guardar(InputStream archivo, String nombreOriginalArchivo) {
        String fileName = StringUtils.cleanPath(nombreOriginalArchivo);

        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("El archivo no contiene un nombre apropiado " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(archivo, targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("No se pudo guardar el archivo con el nombre " + fileName, ex);
        }
    }

    private Resource cargar(String nombre) {
        try {
            Path filePath = this.fileStorageLocation.resolve(nombre).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("Archivo no encontrado " + nombre);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("Arcchivo no encontrado " + nombre, ex);
        }
    }

}
