package com.tesis.transito.persistencia.repositorios;

import com.tesis.transito.entidades.DataGuiaDeTramite;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface GuiasDeTramitesRepositorio extends ReactiveMongoRepository<DataGuiaDeTramite, String> {
    Flux<DataGuiaDeTramite>
    findAllByTituloContainsOrDescripcionContainsOrTipoContains(
            String nombre, String descripcion, String tipo);

    Flux<DataGuiaDeTramite> findAllByTipoContains(String tipo);

    Flux<DataGuiaDeTramite> findDataGuiaDeTramiteByTituloContains(String titulo);
}
