package com.tesis.transito.presentacion.controladores;

import com.sun.tools.javac.util.List;
import com.tesis.transito.dominio.casosdeuso.puntosdeatencion.CasoDeUsoBuscarPuntosDeAtencion;
import com.tesis.transito.dominio.casosdeuso.puntosdeatencion.CasoDeUsoEliminarPuntosDeAtencion;
import com.tesis.transito.dominio.casosdeuso.puntosdeatencion.CasoDeUsoGuardarPuntoDeAtencion;
import com.tesis.transito.dominio.modelos.PuntoDeAtencion;
import com.tesis.transito.dominio.modelos.Tipo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PuntoDeAtencionControlador.class)
@EnableAutoConfiguration
@AutoConfigureDataMongo
@WithMockUser
class PuntoDeAtencionControladorTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CasoDeUsoBuscarPuntosDeAtencion casoDeUsoBuscarPuntosDeAtencion;
    @MockBean
    private CasoDeUsoGuardarPuntoDeAtencion casoDeUsoGuardarPuntoDeAtencion;
    @MockBean
    private CasoDeUsoEliminarPuntosDeAtencion casoDeUsoEliminarPuntoDeAtencion;

    @BeforeEach
    void setUp() {
        PuntoDeAtencion puntoDeAtencionConId = new PuntoDeAtencion("1", "test", "test", "test", "test");
        List<PuntoDeAtencion> puntosDeAtencion = List.of(puntoDeAtencionConId);
        when(casoDeUsoBuscarPuntosDeAtencion.ejecutar(null)).thenReturn(Flux.fromIterable(puntosDeAtencion));
        when(casoDeUsoBuscarPuntosDeAtencion.ejecutar("test")).thenReturn(Flux.fromIterable(puntosDeAtencion));
        when(casoDeUsoGuardarPuntoDeAtencion.ejecutar(puntosDeAtencion)).thenReturn(Flux.fromIterable(puntosDeAtencion));
    }

    @Test
    void buscarPuntosDeAtencion() {
        webTestClient
                .mutateWith(csrf())
                .get()
                .uri("/puntoDeAtencion")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void BuscarPuntosDeAtencionPorNombre() {
        webTestClient
                .mutateWith(csrf())
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/puntoDeAtencion")
                        .queryParam("nombre", "test")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void guardarPuntoDeAtencion() {
        PuntoDeAtencion puntoDeAtencionConId = new PuntoDeAtencion("1", "test", "test", "test", "test");
        List<PuntoDeAtencion> puntosDeAtencion = List.of(puntoDeAtencionConId);

        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/puntoDeAtencion")
                .body(Flux.fromIterable(puntosDeAtencion), PuntoDeAtencion.class)
                .exchange()
                .expectStatus().isOk();
    }
}