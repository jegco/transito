package com.tesis.transito.dominio.casosdeuso.documentos;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.dominio.modelos.Documento;
import com.tesis.transito.dominio.utils.ServicioDeAlmacenamiento;
import org.springframework.core.env.Environment;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CasoDeUsoGuardarDocumento extends CasoDeUsoImpl<FilePart, Documento> {

    private final ServicioDeAlmacenamiento servicioDeAlmacenamiento;
    private final Delegado<String, Documento> delegado;
    private final Environment environment;

    public CasoDeUsoGuardarDocumento(ServicioDeAlmacenamiento servicioDeAlmacenamiento, Delegado<String, Documento> delegado, Environment environment) {
        this.servicioDeAlmacenamiento = servicioDeAlmacenamiento;
        this.delegado = delegado;
        this.environment = environment;
    }

    @Override
    protected Flux<Documento> construirCasoDeUso(FilePart archivoParam) {
        return Flux.from(servicioDeAlmacenamiento.
                guardarDocumento(archivoParam)
                .flatMap(delegado::crear));
    }
}
