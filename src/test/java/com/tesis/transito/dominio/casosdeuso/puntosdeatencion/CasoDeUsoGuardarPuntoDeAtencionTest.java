package com.tesis.transito.dominio.casosdeuso.puntosdeatencion;

import com.sun.tools.javac.util.List;
import com.tesis.transito.dominio.modelos.PuntoDeAtencion;
import com.tesis.transito.persistencia.delegados.PuntoDeAtencionDelegado;
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
class CasoDeUsoGuardarPuntoDeAtencionTest {

    @MockBean
    private PuntoDeAtencionDelegado delegado;

    private CasoDeUsoGuardarPuntoDeAtencion casoDeUso;

    PuntoDeAtencion puntoDeAtencion;

    @BeforeEach
    void setUp() {
        puntoDeAtencion = new PuntoDeAtencion(
                "1",
                "test",
                "test",
                "test",
                "test"
        );
        when(delegado.crear(List.of(puntoDeAtencion))).thenReturn(Flux.fromIterable(List.of(puntoDeAtencion)));
        casoDeUso = new CasoDeUsoGuardarPuntoDeAtencion(delegado);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(List.of(puntoDeAtencion)))
                .expectNextMatches(punto -> punto.getId().equals("1"))
                .expectComplete()
                .verify();
    }
}