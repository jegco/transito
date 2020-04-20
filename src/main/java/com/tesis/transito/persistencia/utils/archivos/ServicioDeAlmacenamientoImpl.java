package com.tesis.transito.persistencia.utils.archivos;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.tesis.transito.dominio.modelos.Documento;
import com.tesis.transito.dominio.utils.ServicioDeAlmacenamiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.InetAddress;
import java.time.LocalDate;

@Service
public class ServicioDeAlmacenamientoImpl implements ServicioDeAlmacenamiento {

    private AmazonS3 client;
    private String endpointUrl;
    private String bucketName;
    private String accessKey;
    private String secretKey;
    private Environment env;

    public ServicioDeAlmacenamientoImpl(@Value("${amazonProperties.endpointUrl}") String endpointUrl,
                                        @Value("${amazonProperties.bucketName}") String bucketName,
                                        @Value("${amazonProperties.accessKey}") String accessKey,
                                        @Value("${amazonProperties.secretKey}") String secretKey,
                                        Environment env) {
        this.endpointUrl = endpointUrl;
        this.bucketName = bucketName;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.env = env;
    }

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.client = new AmazonS3Client(credentials);
    }

    @Override
    public Mono<Documento> guardarDocumento(FilePart archivo) {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + archivo.filename());
        archivo.transferTo(convFile);

        String rutaDescarga = "http://" + InetAddress.getLoopbackAddress().getHostName() + (env.getProperty("server.port") != null ? ":" + env.getProperty("server.port") : "") +
                "/documentos/resource/" +
                archivo.filename();

        return Mono.just(client.putObject(bucketName, convFile.getName(), convFile))
                .map(putObjectResult -> new Documento(null,
                        archivo.filename(),
                        endpointUrl + "/" + bucketName + "/" + archivo.filename(),
                        archivo.filename().substring(archivo.filename().lastIndexOf(".") + 1),
                        LocalDate.now(),
                        LocalDate.now(),
                        rutaDescarga));
    }

    @Override
    public Mono<Resource> cargarDocumento(String nombre) {
        return Mono.just(client.getObject(bucketName, nombre))
                .map(s3Object -> new InputStreamResource(s3Object.getObjectContent()));
    }

    @Override
    public Mono<Boolean> eliminarArchivo(String URIArchivo) {
        return null;
    }

    /* private final Path fileStorageLocation;

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
    } */

}
