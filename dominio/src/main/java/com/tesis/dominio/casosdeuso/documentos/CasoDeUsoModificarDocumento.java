package com.tesis.dominio.casosdeuso.documentos;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.casosdeuso.params.ActualizarArchivoParam;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Documento;
import com.tesis.dominio.utils.StorageService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Date;

@Service
public class CasoDeUsoModificarDocumento extends CasoDeUsoImpl<ActualizarArchivoParam, Documento> {

    private final StorageService servicioDeAlmacenamiento;
    private final Delegado<String, Documento> delegado;

    public CasoDeUsoModificarDocumento(StorageService servicioDeAlmacenamiento, Delegado<String, Documento> delegado) {
        this.servicioDeAlmacenamiento = servicioDeAlmacenamiento;
        this.delegado = delegado;
    }

    @Override
    protected Flux<Documento> construirCasoDeUso(ActualizarArchivoParam archivoParam) {
        return Flux.from(servicioDeAlmacenamiento.
                guardarDocumento(archivoParam.getArchivo(), archivoParam.getNombre())
                .flatMap(direccionArchivo ->
                        delegado.crear(
                                new Documento(null,
                                        archivoParam.getNombre(),
                                        direccionArchivo,
                                        new Date("dd/MM/yyyy HH:MM"),
                                        new Date("dd/MM/yyyy HH:MM"),
                                        direccionArchivo))));
    }
}
