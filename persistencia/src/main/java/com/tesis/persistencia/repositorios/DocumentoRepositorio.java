package com.tesis.persistencia.repositorios;

import com.tesis.persistencia.modelos.DataDocumento;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DocumentoRepositorio extends ReactiveMongoRepository<DataDocumento, String> {
}
