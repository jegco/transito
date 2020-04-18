package com.tesis.persistencia.delegados;

import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Tipo;
import com.tesis.persistencia.repositorios.TiposRepositorio;
import com.tesis.persistencia.utils.DataTipoTipoMapper;
import com.tesis.persistencia.utils.TipoDataTipoMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TiposDelegado implements Delegado<String, Tipo> {

    private final TiposRepositorio repositorio;
    private final DataTipoTipoMapper dataTipoTipoMapper;
    private final TipoDataTipoMapper tipoDataTipoMapper;

    public TiposDelegado(TiposRepositorio repositorio,
                         DataTipoTipoMapper dataTipoTipoMapper,
                         TipoDataTipoMapper tipoDataTipoMapper) {
        this.repositorio = repositorio;
        this.dataTipoTipoMapper = dataTipoTipoMapper;
        this.tipoDataTipoMapper = tipoDataTipoMapper;
    }

    @Override
    public Mono<Tipo> crear(Tipo entidad) {
        return repositorio.save(tipoDataTipoMapper.apply(entidad)).flatMap(dataTipoTipoMapper);
    }

    @Override
    public Flux<Tipo> crear(List<Tipo> entidades) {
        return repositorio.insert(entidades
                .stream()
                .map(tipoDataTipoMapper)
                .collect(Collectors.toList())).flatMap(dataTipoTipoMapper);
    }

    @Override
    public Mono<Tipo> actualizar(Tipo entidad) {
        return repositorio.save(tipoDataTipoMapper.apply(entidad)).flatMap(dataTipoTipoMapper);
    }

    @Override
    public Flux<Tipo> buscar(String s) {
        return s != null ? repositorio.findDataTipoByNombre(s).flatMap(dataTipoTipoMapper)
                : repositorio.findAll().flatMap(dataTipoTipoMapper);
    }

    @Override
    public Mono<Void> eliminar(Tipo entidad) {
        return repositorio.delete(tipoDataTipoMapper.apply(entidad));
    }
}
