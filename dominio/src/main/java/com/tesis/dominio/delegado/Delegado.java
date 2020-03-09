package com.tesis.dominio.delegado;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Delegado<DynamicSearchParam, Entity> {

    Mono<Entity> crear(Entity entidad);
    Mono<Entity> actualizar(Entity entidad);
    Flux<Entity> buscar(DynamicSearchParam param);
    Mono<Void> eliminar(Entity entidad);
}