package com.tesis.transito.dominio.utils;

import com.tesis.transito.dominio.modelos.Documento;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServicioDeAlmacenamiento {

    Mono<Documento> guardarDocumento(Flux<DataBuffer> archivo, String nombreOriginalArchivo);

    Mono<Resource> cargarDocumento(String nombre);

    Mono<Boolean> eliminarArchivo(String URIArchivo);

}
