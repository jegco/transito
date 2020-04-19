package com.tesis.transito.persistencia.repositorios;

import com.tesis.transito.persistencia.modelos.DataDocumento;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface DocumentoRepositorio extends ReactiveMongoRepository<DataDocumento, String> {
    Flux<DataDocumento> findDataDocumentoByNombreContains(String nombre);
}
