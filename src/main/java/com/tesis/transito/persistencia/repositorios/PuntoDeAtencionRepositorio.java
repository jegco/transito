package com.tesis.transito.persistencia.repositorios;

import com.tesis.transito.persistencia.modelos.DataPuntoDeAtencion;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PuntoDeAtencionRepositorio extends ReactiveMongoRepository<DataPuntoDeAtencion, String> {

    Flux<DataPuntoDeAtencion> findDataPuntoDeAtencionByNombre(String nombre);
}
