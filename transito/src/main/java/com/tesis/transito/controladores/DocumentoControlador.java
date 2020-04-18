package com.tesis.transito.controladores;

import com.tesis.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.dominio.casosdeuso.params.ArchivoParam;
import com.tesis.dominio.modelos.Documento;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.*;
import reactor.core.CorePublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/documentos")
@CrossOrigin(value = {"http://localhost:4200"})
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
    public Mono<Documento> guardarDocumento(@RequestBody Flux<Part> archivos) {
        return Mono.from(archivos
                .filter(archivo -> archivo instanceof FilePart)
                .ofType(FilePart.class)
                .flatMap(archivo -> casoDeUsoGuardarDocumentos.ejecutar(new
                        ArchivoParam(archivo.filename(), archivo.content()))));
    }

    @GetMapping("/resource/{nombre}")
    public CorePublisher<Resource> buscarDocumento(@PathVariable String nombre) {
        return casoDeUsoBuscarDocumentos.
                ejecutar(nombre);
    }

    @GetMapping("/{nombre}")
    public CorePublisher<Documento> buscarDocumentosGuardado(@PathVariable String nombre) {
        return casoDeUsoBuscarDocumentosGuardados.ejecutar(nombre);
    }

    @GetMapping()
    public CorePublisher<Documento> buscarDocumentosGuardados() {
        return casoDeUsoBuscarDocumentosGuardados.ejecutar(null);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> eliminarDocumento(@RequestBody Documento documento) {
        return Mono.from(casoDeUsoEliminarDocumento.ejecutar(documento))
                .doOnError(error -> System.out.println(error.getMessage()));
    }
}
