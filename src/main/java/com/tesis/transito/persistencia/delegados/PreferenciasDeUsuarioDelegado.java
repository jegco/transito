package com.tesis.transito.persistencia.delegados;

import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.entidades.PreferenciasDeUsuario;
import com.tesis.transito.persistencia.repositorios.PreferenciasDeUsuarioRepositorio;
import com.tesis.transito.persistencia.utils.DataPreferenciasDeUsuarioPreferenciasDeUsuarioMapper;
import com.tesis.transito.persistencia.utils.PreferenciasDeUsuarioDataPreferenciasDeUsuarioMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

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
    public Flux<PreferenciasDeUsuario> crear(List<PreferenciasDeUsuario> entidades) {
        return repositorio.insert(entidades
                .stream()
                .map(menuDataMenuMapper)
                .collect(Collectors.toList())).map(dataMenuMenuMapper);
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
