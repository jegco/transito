package com.tesis.transito.persistencia.utils.archivos;

import com.dropbox.core.v2.DbxClientV2;
import com.tesis.transito.entidades.Documento;
import com.tesis.transito.dominio.utils.ServicioDeAlmacenamiento;
import com.tesis.transito.persistencia.utils.archivos.excepciones.FileStorageException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.time.LocalDate;

@Service
public class ServicioDeAlmacenamientoImpl implements ServicioDeAlmacenamiento {

    private final DbxClientV2 client;
    private final Environment env;

    public ServicioDeAlmacenamientoImpl(DbxClientV2 client,
                                        Environment env) {
        this.client = client;
        this.env = env;
    }

    @Override
    public Mono<Documento> guardarDocumento(FilePart archivo) {
        try {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + archivo.filename());
            archivo.transferTo(convFile);


            FileInputStream inputStream = new FileInputStream(convFile);

            String rutaDescarga = "http://" + InetAddress.getLoopbackAddress().getHostName() + (env.getProperty("server.port") != null ? ":" + env.getProperty("server.port") : "") +
                    "/documentos/resource/" +
                    archivo.filename();

            return Mono.just(handleDropboxAction(() -> client.files().upload("/" + convFile.getName()).uploadAndFinish(inputStream),
                            String.format("Error subiendo archivo: %s", convFile.getName())))
                    .map(putObjectResult -> new Documento(null,
                            archivo.filename(),
                            archivo.filename(),
                            archivo.filename().substring(archivo.filename().lastIndexOf(".") + 1),
                            LocalDate.now(),
                            LocalDate.now(),
                            rutaDescarga));
        } catch (FileNotFoundException e) {
            throw new com.tesis.transito.persistencia.utils.
                    archivos.excepciones.
                    FileNotFoundException(String.format("Error subiendo archivo: %s", archivo.filename()));
        }
    }

    @Override
    public Mono<Resource> cargarDocumento(String nombre) {
        return Mono.just(handleDropboxAction(() -> client.files().download(nombre).getInputStream(),
                        String.format("Error downloading file: %s", nombre)))
                .map(InputStreamResource::new);
    }

    @Override
    public Mono<Boolean> eliminarArchivo(String nombre) {
        try {
            handleDropboxAction(() -> client.files().deleteV2(nombre), String.format("Error deleting file: %s", nombre));
            return Mono.just(true);
        }catch ( RuntimeException e) {
            return Mono.just(false);
        }
    }

    private <T> T handleDropboxAction(DropboxActionResolver<T> action, String exceptionMessage) {
        try {
            return action.perform();
        } catch (Exception e) {
            String messageWithCause = String.format("%s with cause: %s", exceptionMessage, e.getMessage());
            throw new FileStorageException(messageWithCause, e);
        }
    }

}
