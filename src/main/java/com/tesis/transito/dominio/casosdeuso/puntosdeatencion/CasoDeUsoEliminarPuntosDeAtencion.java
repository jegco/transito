package com.tesis.transito.dominio.casosdeuso.puntosdeatencion;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.entidades.PuntoDeAtencion;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CasoDeUsoEliminarPuntosDeAtencion extends CasoDeUsoImpl<PuntoDeAtencion, Void> {

    private final Delegado<String, PuntoDeAtencion> delegado;

    public CasoDeUsoEliminarPuntosDeAtencion(Delegado<String, PuntoDeAtencion> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Mono<Void> construirCasoDeUso(PuntoDeAtencion puntoDeAtencion) {
        return delegado.eliminar(puntoDeAtencion);
    }
}
