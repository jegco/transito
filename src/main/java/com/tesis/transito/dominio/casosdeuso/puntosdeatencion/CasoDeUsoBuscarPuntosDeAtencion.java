package com.tesis.transito.dominio.casosdeuso.puntosdeatencion;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.entidades.PuntoDeAtencion;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CasoDeUsoBuscarPuntosDeAtencion extends CasoDeUsoImpl<String, PuntoDeAtencion> {

    private final Delegado<String, PuntoDeAtencion> delegado;

    public CasoDeUsoBuscarPuntosDeAtencion(Delegado<String, PuntoDeAtencion> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Flux<PuntoDeAtencion> construirCasoDeUso(String s) {
        return delegado.buscar(s);
    }
}
