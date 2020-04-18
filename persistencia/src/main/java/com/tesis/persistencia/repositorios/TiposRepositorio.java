package com.tesis.persistencia.repositorios;

import com.tesis.persistencia.modelos.DataTipo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TiposRepositorio extends ReactiveMongoRepository<DataTipo, String> {

    Flux<DataTipo> findDataTipoByNombre(String nombre);
}
