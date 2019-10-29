package com.tesis.persistencia.delegados;

import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Documento;
import com.tesis.persistencia.repositorios.DocumentoRepositorio;
import com.tesis.persistencia.utils.DataDocumentoDocumentoMapper;
import com.tesis.persistencia.utils.DocumentoDataDocumentoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<Documento> actualizar(Documento entidad) {
        return repositorio.save(documentoDataDocumentoMapper.apply(entidad)).map(dataDocumentoDocumentoMapper);
    }

    @Override
    public Flux<Documento> buscar(String param) {
        return repositorio.findDataDocumentoByNombreContains(param)
                .map(dataDocumentoDocumentoMapper);
    }

    @Override
    public Mono<Void> eliminar(Documento entidad) {
        return repositorio.delete(documentoDataDocumentoMapper.apply(entidad));
    }
}
