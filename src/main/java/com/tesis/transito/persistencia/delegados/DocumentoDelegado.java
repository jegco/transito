package com.tesis.transito.persistencia.delegados;

import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.dominio.modelos.Documento;
import com.tesis.transito.persistencia.repositorios.DocumentoRepositorio;
import com.tesis.transito.persistencia.utils.DataDocumentoDocumentoMapper;
import com.tesis.transito.persistencia.utils.DocumentoDataDocumentoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoDelegado implements Delegado<String, Documento> {

    private final DocumentoRepositorio repositorio;
    private final DataDocumentoDocumentoMapper dataDocumentoDocumentoMapper;
    private final DocumentoDataDocumentoMapper documentoDataDocumentoMapper;

    @Autowired
    public DocumentoDelegado(DocumentoRepositorio repositorio,
                             DataDocumentoDocumentoMapper dataDocumentoDocumentoMapper,
                             DocumentoDataDocumentoMapper documentoDataDocumentoMapper) {
        this.repositorio = repositorio;
        this.dataDocumentoDocumentoMapper = dataDocumentoDocumentoMapper;
        this.documentoDataDocumentoMapper = documentoDataDocumentoMapper;
    }

    @Override
    public Mono<Documento> crear(Documento entidad) {
        return repositorio.insert(documentoDataDocumentoMapper.apply(entidad)).map(dataDocumentoDocumentoMapper);
    }

    @Override
    public Flux<Documento> crear(List<Documento> entidades) {
        return repositorio.insert(entidades
                .stream()
                .map(documentoDataDocumentoMapper)
                .collect(Collectors.toList())).map(dataDocumentoDocumentoMapper);
    }

    @Override
    public Mono<Documento> actualizar(Documento entidad) {
        return repositorio.save(documentoDataDocumentoMapper.apply(entidad)).map(dataDocumentoDocumentoMapper);
    }

    @Override
    public Flux<Documento> buscar(String param) {
        return param != null ? repositorio.findDataDocumentoByNombreContains(param)
                .map(dataDocumentoDocumentoMapper) : repositorio.findAll().map(dataDocumentoDocumentoMapper);
    }

    @Override
    public Mono<Void> eliminar(Documento entidad) {
        return repositorio.delete(documentoDataDocumentoMapper.apply(entidad));
    }
}
