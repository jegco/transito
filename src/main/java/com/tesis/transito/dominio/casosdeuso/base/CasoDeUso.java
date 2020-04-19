package com.tesis.transito.dominio.casosdeuso.base;

import reactor.core.CorePublisher;

public interface CasoDeUso<Params, Resp> {
    CorePublisher<Resp> ejecutar(Params params);
}
