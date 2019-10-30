package com.tesis.persistencia.repositorios;

import com.tesis.persistencia.modelos.DataGuiaDeTramite;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface GuiasDeTramitesRepositorio extends ReactiveMongoRepository<DataGuiaDeTramite, String> {
    Flux<DataGuiaDeTramite>
    findAllByTituloContainsOrDescripcionContainsOrTipoContains(
            String nombre, String descripcion, String tipo);

    Flux<DataGuiaDeTramite> findAllByTipoContains(String tipo);
}
