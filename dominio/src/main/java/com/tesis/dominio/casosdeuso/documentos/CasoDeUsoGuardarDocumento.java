package com.tesis.dominio.casosdeuso.documentos;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.casosdeuso.params.ArchivoParam;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Documento;
import com.tesis.dominio.utils.ServicioDeAlmacenamiento;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.net.InetAddress;
import java.time.LocalDate;

@Service
public class CasoDeUsoGuardarDocumento extends CasoDeUsoImpl<ArchivoParam, Documento> {

    private final ServicioDeAlmacenamiento servicioDeAlmacenamiento;
    private final Delegado<String, Documento> delegado;
    private final Environment environment;

    public CasoDeUsoGuardarDocumento(ServicioDeAlmacenamiento servicioDeAlmacenamiento, Delegado<String, Documento> delegado, Environment environment) {
        this.servicioDeAlmacenamiento = servicioDeAlmacenamiento;
        this.delegado = delegado;
        this.environment = environment;
    }

    @Override
    protected Flux<Documento> construirCasoDeUso(ArchivoParam archivoParam) {
        return Flux.from(servicioDeAlmacenamiento.
                guardarDocumento(archivoParam.getArchivo(), archivoParam.getNombre())
                .map(rutaArchivo -> new Pair<>(rutaArchivo, rutaArchivo.substring(rutaArchivo.lastIndexOf(".") + 1)))
                .flatMap(descripcionArchivo ->
                        delegado.crear(
                                new Documento(null,
                                        archivoParam.getNombre(),
                                        descripcionArchivo.getKey(),
                                        descripcionArchivo.getValue(),
                                        LocalDate.now(),
                                        LocalDate.now(),
                                        InetAddress.getLoopbackAddress().getHostName() + ":" +
                                                environment.getProperty("server.port") + "/documentos/resource/" + archivoParam.getNombre()))));
    }
}
