package com.tesis.transito.persistencia.repositorios;

import com.tesis.transito.entidades.DataPreferenciasDeUsuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PreferenciasDeUsuarioRepositorio extends ReactiveMongoRepository<DataPreferenciasDeUsuario, String> {
}
