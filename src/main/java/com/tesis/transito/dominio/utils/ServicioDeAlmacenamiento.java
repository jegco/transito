package com.tesis.transito.dominio.utils;

import com.tesis.transito.entidades.Documento;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface ServicioDeAlmacenamiento {

    Mono<Documento> guardarDocumento(FilePart arcivo);

    Mono<Resource> cargarDocumento(String nombre);

    Mono<Boolean> eliminarArchivo(String nombre);

}
