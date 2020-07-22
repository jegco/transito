package com.tesis.transito.dominio.casosdeuso.documentos;

import com.sun.tools.javac.util.List;
import com.tesis.transito.dominio.modelos.Documento;
import com.tesis.transito.persistencia.delegados.DocumentoDelegado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class CasoDeUsoBuscarDocumentosTest {

    @MockBean
    private DocumentoDelegado delegado;

    private CasoDeUsoBuscarDocumentos casoDeUso;

    @BeforeEach
    void setUp() {
        Documento documento = Documento.builder().id("1").rutaDeDescarga("test anexo").nombre("test anexo").build();
        when(delegado.buscar(null)).thenReturn(Flux.fromIterable(List.of(documento)));
        casoDeUso = new CasoDeUsoBuscarDocumentos(delegado);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(null))
                .expectNextMatches(documento -> documento.getId().equals("1"))
                .expectComplete()
                .verify();
    }
}