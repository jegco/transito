package com.tesis.transito.persistencia.utils.archivos;

import com.tesis.transito.dominio.modelos.Documento;
import com.tesis.transito.dominio.utils.ServicioDeAlmacenamiento;
import com.tesis.transito.persistencia.utils.archivos.excepciones.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ServicioDeAlmacenamientoImpl implements ServicioDeAlmacenamiento {

    private final Path fileStorageLocation;

    @Autowired
    public ServicioDeAlmacenamientoImpl(FileStorageProperties fileStorageProperties) {

        try {
            this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                    .toAbsolutePath().normalize();
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    @Override
    public Mono<Documento> guardarDocumento(Flux<DataBuffer> archivo, String nombreOriginalArchivo) {
        final String filename = nombreOriginalArchivo;
        File file = new File(fileStorageLocation.toString() + "/" + filename);
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
            }).last().map(dataBuffer -> new Documento(null,
                    file.getPath(),
                    file.getName(),
                    file.getPath().substring(file.getPath().lastIndexOf(".") + 1),
                    LocalDate.now(),
                    LocalDate.now(),
                    file.getPath().substring(file.getPath().lastIndexOf(".") + 1)));
        } catch (IOException e) {
            // unable to open the file channel, return an error

            return Mono.error(e);
        }
    }

    @Override
    public Mono<Resource> cargarDocumento(String nombre) {
        return Mono.just(cargar(nombre)).handle((archivo, sink) -> {
            if (archivo == null) {
                sink.error(new Exception("El archivo no pudo ser encontrado"));
            } else {
                sink.next(archivo);
            }
        });
    }

    @Override
    public Mono<Boolean> eliminarArchivo(String nombreArchivo) {
        return Mono.just(eliminar(nombreArchivo)).handle((eliminado, sink) -> {
        if (!eliminado) {
            sink.error(new Exception("el archivo no pudo ser eliminado"));
        } else {
            sink.next(eliminado);
        }});
    }

    private Resource cargar(String nombre) {
        try {
            Path filePath = this.fileStorageLocation.resolve(nombre).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    private boolean eliminar(String nombre){
        try {
            Path filePath = this.fileStorageLocation.resolve(nombre).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                try {
                    return resource.getFile().delete();
                } catch (IOException e) {
                    return false;
                }
            } else {
                return false;
            }
        } catch (MalformedURLException ex) {
            return false;
        }
    }

}
