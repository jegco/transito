package com.tesis.transito.dominio.delegado;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface Delegado<DynamicSearchParam, Entity> {

    Mono<Entity> crear(Entity entidad);
    Flux<Entity> crear(List<Entity> entidades);
    Mono<Entity> actualizar(Entity entidad);
    Flux<Entity> buscar(DynamicSearchParam param);
    Mono<Void> eliminar(Entity entidad);
}