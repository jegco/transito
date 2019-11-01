package com.tesis.transito.controladores;

import com.tesis.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.dominio.casosdeuso.params.ArchivoParam;
import com.tesis.dominio.modelos.Documento;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;

@RestController
@RequestMapping(path = "/documentos")
@CrossOrigin(value = { "http://localhost:4200" })
public class DocumentoControlador {

    private final CasoDeUso<ArchivoParam, Documento> casoDeUsoGuardarDocumentos;
    private final CasoDeUso<String, Resource> casoDeUsoBuscarDocumentos;

    public DocumentoControlador(CasoDeUso<ArchivoParam, Documento> casoDeUsoGuardarDocumento,
                                CasoDeUso<String, Resource> casoDeUsoBuscarDocumentos) {
        this.casoDeUsoGuardarDocumentos = casoDeUsoGuardarDocumento;
        this.casoDeUsoBuscarDocumentos = casoDeUsoBuscarDocumentos;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono<Documento> guardarArchivos(@RequestBody Flux<Part> archivos) throws IOException {
        return Mono.from(archivos
                .filter(archivo -> archivo instanceof FilePart)
                .ofType(FilePart.class)
                .flatMap(archivo -> casoDeUsoGuardarDocumentos.ejecutar(new
                        ArchivoParam(archivo.filename(), archivo.content()))));
    }

    @GetMapping("/{nombre}")
    public Flux<Resource> buscarDocumentos(@PathVariable String nombre) {
        return casoDeUsoBuscarDocumentos.
                ejecutar(nombre);
    }
}
