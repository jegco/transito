package com.tesis.transito.persistencia.utils;

import com.tesis.transito.dominio.modelos.Paso;
import com.tesis.transito.persistencia.modelos.DataPaso;
import com.tesis.transito.persistencia.repositorios.DocumentoRepositorio;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DataPasoPasoMapper implements Function<List<DataPaso>, Mono<List<Paso>>> {

    private final DataDocumentoDocumentoMapper mapper;
    private final DocumentoRepositorio repositorio;

    public DataPasoPasoMapper(DataDocumentoDocumentoMapper mapper, DocumentoRepositorio repositorio) {
        this.mapper = mapper;
        this.repositorio = repositorio;
    }

    @Override
    public Mono<List<Paso>> apply(List<DataPaso> dataPaso) {
        return Flux.fromIterable(dataPaso)
                .flatMap(paso -> paso.getAnexo() != null ? repositorio.findById(paso.getAnexo())
                        .map(documento -> {
                            Paso p = new Paso(paso.getTitulo(), paso.getDescripcion());
                            p.setAnexo(mapper.apply(documento));
                            return p;
                        }) :  Mono.just(new Paso(paso.getTitulo(), paso.getDescripcion()))).collectList();
    }
}
