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

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class CasoDeUsoBuscarPuntosDeAtencionTest {

    @MockBean
    private PuntoDeAtencionDelegado delegado;

    private CasoDeUsoBuscarPuntosDeAtencion casoDeUso;

    @BeforeEach
    void setUp() {
        PuntoDeAtencion puntoDeAtencion = new PuntoDeAtencion(
                "1",
                "test",
                "test",
                "test",
                "test"
        );
        when(delegado.buscar(null)).thenReturn(Flux.just(puntoDeAtencion));
        casoDeUso = new CasoDeUsoBuscarPuntosDeAtencion(delegado);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(null))
                .expectNextMatches(puntoDeAtencion -> puntoDeAtencion.getId().equals("1"))
                .expectComplete()
                .verify();
    }
}