package com.tesis.persistencia.utils;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;

@Service
public class StorageServiceImpl implements StorageService {
    @Override
    public String guardarDocumentos(File archivo) {
        return null;
    }

    @Override
    public File cargarDocumento(String nombre) {
        return null;
    }

    @Override
    public File cargarDocumentos() {
        return null;
    }

    @Override
    public Mono<Void> eliminarArchivo(File archivo) {
        return null;
    }
}
