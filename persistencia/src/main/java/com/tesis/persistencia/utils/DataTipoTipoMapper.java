package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.Tipo;
import com.tesis.persistencia.modelos.DataTipo;
import com.tesis.persistencia.repositorios.DocumentoRepositorio;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class DataTipoTipoMapper implements Function<DataTipo, Mono<Tipo>> {

    private final DataDocumentoDocumentoMapper mapper;
    private final DocumentoRepositorio repositorio;

    public DataTipoTipoMapper(DataDocumentoDocumentoMapper mapper, DocumentoRepositorio repositorio) {
        this.mapper = mapper;
        this.repositorio = repositorio;
    }

    @Override
    public Mono<Tipo> apply(DataTipo dataTipo) {
        return Mono.just(new Tipo(dataTipo.getId(),
                dataTipo.getNombre()))
                .zipWith(repositorio.findById(dataTipo.getIcono()), (tipo, documento) -> {
                    tipo.setIcono(mapper.apply(documento));
                    return tipo;
                });
    }
}
