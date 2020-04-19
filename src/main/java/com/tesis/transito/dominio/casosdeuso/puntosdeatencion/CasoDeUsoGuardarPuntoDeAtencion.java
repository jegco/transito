package com.tesis.transito.dominio.casosdeuso.puntosdeatencion;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.dominio.modelos.PuntoDeAtencion;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class CasoDeUsoGuardarPuntoDeAtencion extends CasoDeUsoImpl<List<PuntoDeAtencion>, PuntoDeAtencion> {

    private final Delegado<String, PuntoDeAtencion> delegado;

    public CasoDeUsoGuardarPuntoDeAtencion(Delegado<String, PuntoDeAtencion> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Flux<PuntoDeAtencion> construirCasoDeUso(List<PuntoDeAtencion> puntoDeAtencion) {
        return delegado.crear(puntoDeAtencion);
    }
}
