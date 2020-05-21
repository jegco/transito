package com.tesis.transito.dominio.utils;

import com.tesis.transito.dominio.modelos.Documento;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServicioDeAlmacenamiento {

    Mono<Documento> guardarDocumento(FilePart arcivo);

    Mono<Resource> cargarDocumento(String nombre);

    Mono<Boolean> eliminarArchivo(String nombre);

}
