package com.tesis.dominio.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.InputStream;

public interface StorageService {

    Mono<String> guardarDocumento(Flux<DataBuffer> archivo, String nombreOriginalArchivo);

    Mono<Resource> cargarDocumento(String nombre);

    Mono<Void> eliminarArchivo(File archivo);

}
