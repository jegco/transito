package com.tesis.persistencia.repositorios;

import com.tesis.persistencia.modelos.DataUsuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UsuarioRepositorio extends ReactiveMongoRepository<DataUsuario, String> {

    Flux<DataUsuario> findDataUsuarioByNombreAndPasswordAndActive(String nombre, String password, boolean active);

    Flux<DataUsuario> findDataUsuarioByActiveAndRol(boolean active, String rol);

    Flux<DataUsuario> findDataUsuarioByActive(boolean active);
}
