package com.tesis.transito.dominio.casosdeuso.puntosdeatencion;

import com.tesis.transito.entidades.PuntoDeAtencion;
import com.tesis.transito.persistencia.delegados.PuntoDeAtencionDelegado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class CasoDeUsoEliminarPuntosDeAtencionTest {

    @MockBean
    private PuntoDeAtencionDelegado delegado;

    private CasoDeUsoEliminarPuntosDeAtencion casoDeUso;

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
        when(delegado.eliminar(puntoDeAtencion)).thenReturn(Mono.empty());
        casoDeUso = new CasoDeUsoEliminarPuntosDeAtencion(delegado);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(puntoDeAtencion))
                .expectComplete()
                .verify();
    }
}