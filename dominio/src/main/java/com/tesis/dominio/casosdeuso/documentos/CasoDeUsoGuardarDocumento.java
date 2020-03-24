package com.tesis.dominio.casosdeuso.documentos;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.casosdeuso.params.ArchivoParam;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Documento;
import com.tesis.dominio.utils.ServicioDeAlmacenamiento;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Date;

@Service
public class CasoDeUsoGuardarDocumento extends CasoDeUsoImpl<ArchivoParam, Documento> {

    private final ServicioDeAlmacenamiento servicioDeAlmacenamiento;
    private final Delegado<String, Documento> delegado;

    public CasoDeUsoGuardarDocumento(ServicioDeAlmacenamiento servicioDeAlmacenamiento, Delegado<String, Documento> delegado) {
        this.servicioDeAlmacenamiento = servicioDeAlmacenamiento;
        this.delegado = delegado;
    }

    @Override
    protected Flux<Documento> construirCasoDeUso(ArchivoParam archivoParam) {
        return Flux.from(servicioDeAlmacenamiento.
                guardarDocumento(archivoParam.getArchivo(), archivoParam.getNombre())
                .flatMap(direccionArchivo ->
                        delegado.crear(
                                new Documento(null,
                                        archivoParam.getNombre(),
                                        direccionArchivo,
                                        new Date(),
                                        new Date(),
                                        direccionArchivo))));
    }
}
