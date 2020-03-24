package com.tesis.transito.controladores;

import com.tesis.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.dominio.casosdeuso.params.ArchivoParam;
import com.tesis.dominio.modelos.Documento;
import com.tesis.transito.utils.PageSupport;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;

import static com.tesis.transito.utils.PageSupport.DEFAULT_PAGE_SIZE;
import static com.tesis.transito.utils.PageSupport.FIRST_PAGE_NUM;

@RestController
@RequestMapping(path = "/documentos")
@CrossOrigin(value = { "http://localhost:4200" })
public class DocumentoControlador {

    private final CasoDeUso<ArchivoParam, Documento> casoDeUsoGuardarDocumentos;
    private final CasoDeUso<String, Resource> casoDeUsoBuscarDocumentos;
    private final CasoDeUso<String, Documento> casoDeUsoBuscarDocumentosGuardados;
    private final CasoDeUso<Documento, Void> casoDeUsoEliminarDocumento;

    public DocumentoControlador(CasoDeUso<ArchivoParam, Documento> casoDeUsoGuardarDocumento,
                                CasoDeUso<String, Resource> casoDeUsoBuscarDocumentos,
                                CasoDeUso<String, Documento> casoDeUsoBuscarDocumentosGuardados,
                                CasoDeUso<Documento, Void> casoDeUsoEliminarDocumento) {
        this.casoDeUsoGuardarDocumentos = casoDeUsoGuardarDocumento;
        this.casoDeUsoBuscarDocumentos = casoDeUsoBuscarDocumentos;
        this.casoDeUsoBuscarDocumentosGuardados = casoDeUsoBuscarDocumentosGuardados;
        this.casoDeUsoEliminarDocumento = casoDeUsoEliminarDocumento;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono<Documento> guardarDocumento(@RequestBody Flux<Part> archivos) throws IOException {
        return Mono.from(archivos
                .filter(archivo -> archivo instanceof FilePart)
                .ofType(FilePart.class)
                .flatMap(archivo -> casoDeUsoGuardarDocumentos.ejecutar(new
                        ArchivoParam(archivo.filename(), archivo.content()))));
    }

    @GetMapping("/resource/{nombre}")
    public Flux<Resource> buscarDocumento(@PathVariable String nombre) {
        return casoDeUsoBuscarDocumentos.
                ejecutar(nombre);
    }

    @GetMapping("/{nombre}")
    public Flux<Documento> buscarDocumentosGuardado(@PathVariable String nombre) {
        return casoDeUsoBuscarDocumentosGuardados.ejecutar(nombre);
    }

    @GetMapping()
    public Flux<Documento> buscarDocumentosGuardados(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        return casoDeUsoBuscarDocumentosGuardados.ejecutar(null)
                .skip(page * size)
                .limitRequest(size);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<Void> eliminarDocumento(@RequestBody Documento documento) {
        return casoDeUsoEliminarDocumento.ejecutar(documento).doOnError(error -> System.out.println(error.getMessage()));
    }
}
