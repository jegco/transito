package com.tesis.dominio.casosdeuso.base;

import reactor.core.CorePublisher;

public abstract class CasoDeUsoImpl<Params, Resp> implements CasoDeUso<Params, Resp> {
    @Override
    public CorePublisher<Resp> ejecutar(Params params) {
        return construirCasoDeUso(params);
    }

    protected abstract CorePublisher<Resp> construirCasoDeUso(Params params);
}
