package com.tesis.transito.dominio.casosdeuso.documentos;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.casosdeuso.params.ActualizarArchivoParam;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.entidades.Documento;
import com.tesis.transito.dominio.utils.ServicioDeAlmacenamiento;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CasoDeUsoModificarDocumento extends CasoDeUsoImpl<ActualizarArchivoParam, Documento> {

    private final ServicioDeAlmacenamiento servicioDeAlmacenamiento;
    private final Delegado<String, Documento> delegado;

    public CasoDeUsoModificarDocumento(ServicioDeAlmacenamiento servicioDeAlmacenamiento, Delegado<String, Documento> delegado) {
        this.servicioDeAlmacenamiento = servicioDeAlmacenamiento;
        this.delegado = delegado;
    }

    @Override
    protected Mono<Documento> construirCasoDeUso(ActualizarArchivoParam archivoParam) {
        return servicioDeAlmacenamiento.
                guardarDocumento(archivoParam.getArchivo())
                .flatMap(documentoNuevo -> {
                    archivoParam.getDocumento().setRutaDeDescarga(documentoNuevo.getRutaDeDescarga());
                    return delegado.actualizar(archivoParam.getDocumento());
                });
    }
}
