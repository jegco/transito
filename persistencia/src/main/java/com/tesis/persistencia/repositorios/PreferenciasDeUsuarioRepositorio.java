package com.tesis.persistencia.repositorios;

import com.tesis.persistencia.modelos.DataMenu;
import com.tesis.persistencia.modelos.DataPreferenciasDeUsuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PreferenciasDeUsuarioRepositorio extends ReactiveMongoRepository<DataPreferenciasDeUsuario, String> {
}
