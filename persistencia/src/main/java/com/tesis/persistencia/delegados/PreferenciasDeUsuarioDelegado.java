package com.tesis.persistencia.delegados;

import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.PreferenciasDeUsuario;
import com.tesis.persistencia.repositorios.PreferenciasDeUsuarioRepositorio;
import com.tesis.persistencia.utils.DataPreferenciasDeUsuarioPreferenciasDeUsuarioMapper;
import com.tesis.persistencia.utils.PreferenciasDeUsuarioDataPreferenciasDeUsuarioMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PreferenciasDeUsuarioDelegado implements Delegado<String, PreferenciasDeUsuario> {

    private final PreferenciasDeUsuarioRepositorio repositorio;
    private final PreferenciasDeUsuarioDataPreferenciasDeUsuarioMapper menuDataMenuMapper;
    private final DataPreferenciasDeUsuarioPreferenciasDeUsuarioMapper dataMenuMenuMapper;

    public PreferenciasDeUsuarioDelegado(PreferenciasDeUsuarioRepositorio repositorio,
                                         PreferenciasDeUsuarioDataPreferenciasDeUsuarioMapper menuDataMenuMapper,
                                         DataPreferenciasDeUsuarioPreferenciasDeUsuarioMapper dataMenuMenuMapper) {
        this.repositorio = repositorio;
        this.menuDataMenuMapper = menuDataMenuMapper;
        this.dataMenuMenuMapper = dataMenuMenuMapper;
    }

    @Override
    public Mono<PreferenciasDeUsuario> crear(PreferenciasDeUsuario entidad) {
        return repositorio.save(menuDataMenuMapper.apply(entidad)).map(dataMenuMenuMapper);
    }

    @Override
    public Mono<PreferenciasDeUsuario> actualizar(PreferenciasDeUsuario entidad) {
        return repositorio.save(menuDataMenuMapper.apply(entidad)).map(dataMenuMenuMapper);
    }

    @Override
    public Flux<PreferenciasDeUsuario> buscar(String s) {
        return repositorio.findAll().map(dataMenuMenuMapper);
    }

    @Override
    public Mono<Void> eliminar(PreferenciasDeUsuario entidad) {
        return repositorio.delete(menuDataMenuMapper.apply(entidad));
    }
}
