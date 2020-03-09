package com.tesis.dominio.casosdeuso.documentos;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Documento;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CasoDeUsoBuscarDocumentos extends CasoDeUsoImpl<String, Documento> {

    private final Delegado<String, Documento> delegado;

    public CasoDeUsoBuscarDocumentos(Delegado<String, Documento> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Flux<Documento> construirCasoDeUso(String param) {
        return delegado.buscar(param);
    }
}
