package com.tesis.transito.persistencia.utils.archivos;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.tesis.transito.entidades.Documento;
import com.tesis.transito.dominio.utils.ServicioDeAlmacenamiento;
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
    public Mono<Boolean> eliminarArchivo(String nombre) {
        try {
            client.deleteObject(bucketName, nombre);
            return Mono.just(true);
        }catch ( AmazonClientException e) {
            return Mono.just(false);
        }
    }
}
