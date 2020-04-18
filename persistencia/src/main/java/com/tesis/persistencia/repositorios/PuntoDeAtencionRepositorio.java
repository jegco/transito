package com.tesis.persistencia.repositorios;

import com.tesis.persistencia.modelos.DataPuntoDeAtencion;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PuntoDeAtencionRepositorio extends ReactiveMongoRepository<DataPuntoDeAtencion, String> {

    Flux<DataPuntoDeAtencion> findDataPuntoDeAtencionByNombre(String nombre);
}
