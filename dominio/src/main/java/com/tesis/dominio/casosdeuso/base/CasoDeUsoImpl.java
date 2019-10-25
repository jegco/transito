package com.tesis.dominio.casosdeuso.base;

import reactor.core.publisher.Flux;

public abstract class CasoDeUsoImpl<Params, Resp> implements CasoDeUso<Params, Resp> {
    @Override
    public Flux<Resp> ejecutar(Params params) {
        return construirCasoDeUso(params);
    }

    protected abstract Flux<Resp> construirCasoDeUso(Params params);
}
