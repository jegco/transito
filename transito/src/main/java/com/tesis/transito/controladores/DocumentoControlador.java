package com.tesis.transito.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/documentos")
public class DocumentoControlador {

    @GetMapping
    public Mono<String> test() {
        return Mono.just("testing");
    }
}
