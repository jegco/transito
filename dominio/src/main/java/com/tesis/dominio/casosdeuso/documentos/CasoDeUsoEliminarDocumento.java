package com.tesis.dominio.casosdeuso.documentos;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Documento;
import com.tesis.dominio.utils.StorageService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CasoDeUsoEliminarDocumento extends CasoDeUsoImpl<Documento, Void> {

    private final StorageService servicioDeAlmacenamiento;
    private final Delegado<String, Documento> delegado;

    public CasoDeUsoEliminarDocumento(StorageService servicioDeAlmacenamiento, Delegado<String, Documento> delegado) {
        this.servicioDeAlmacenamiento = servicioDeAlmacenamiento;
        this.delegado = delegado;
    }

    @Override
    protected Flux<Void> construirCasoDeUso(Documento documento) {
        return Flux.from(servicioDeAlmacenamiento.eliminarArchivo(documento.getArchivo())
                .flatMap(eliminado -> delegado.eliminar(documento)));
    }
}
