package com.tesis.transito.dominio.casosdeuso.puntosdeatencion;

import com.tesis.transito.entidades.PuntoDeAtencion;
import com.tesis.transito.persistencia.delegados.PuntoDeAtencionDelegado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class CasoDeUsoGuardarPuntoDeAtencionTest {

    @MockBean
    private PuntoDeAtencionDelegado delegado;

    private CasoDeUsoGuardarPuntoDeAtencion casoDeUso;

    List<PuntoDeAtencion> puntosDeAtencion;

    @BeforeEach
    void setUp() {
        PuntoDeAtencion puntoDeAtencion = new PuntoDeAtencion(
                "1",
                "test",
                "test",
                "test",
                "test"
        );
        puntosDeAtencion = new ArrayList<>();
        puntosDeAtencion.add(puntoDeAtencion);
        when(delegado.crear(puntosDeAtencion)).thenReturn(Flux.fromIterable(puntosDeAtencion));
        casoDeUso = new CasoDeUsoGuardarPuntoDeAtencion(delegado);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(puntosDeAtencion))
                .expectNextMatches(punto -> punto.getId().equals("1"))
                .expectComplete()
                .verify();
    }
}