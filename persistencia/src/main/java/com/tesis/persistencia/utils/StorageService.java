package com.tesis.persistencia.utils;

import reactor.core.publisher.Mono;

import java.io.File;

public interface StorageService {

    String guardarDocumentos(File archivo);

    File cargarDocumento(String nombre);

    File cargarDocumentos();

    Mono<Void> eliminarArchivo(File archivo);

}
