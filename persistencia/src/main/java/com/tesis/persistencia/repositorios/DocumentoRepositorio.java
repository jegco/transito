package com.tesis.persistencia.repositorios;

import com.tesis.persistencia.modelos.DataDocumento;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface DocumentoRepositorio extends ReactiveMongoRepository<DataDocumento, String> {
    Flux<DataDocumento> findDataDocumentoByNombreContains(String nombre);
}
