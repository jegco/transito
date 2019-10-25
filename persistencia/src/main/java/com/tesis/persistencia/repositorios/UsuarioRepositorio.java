package com.tesis.persistencia.repositorios;

import com.tesis.persistencia.modelos.DataUsuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends ReactiveMongoRepository<DataUsuario, String> {
}
