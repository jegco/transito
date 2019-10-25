package com.tesis.dominio.casosdeuso.base;

import reactor.core.publisher.Flux;

public interface CasoDeUso<Params, Resp> {
    Flux<Resp> ejecutar(Params params);
}
