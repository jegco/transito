package com.tesis.transito.integracion;

import com.tesis.transito.dominio.casosdeuso.puntosdeatencion.CasoDeUsoBuscarPuntosDeAtencion;
import com.tesis.transito.dominio.casosdeuso.puntosdeatencion.CasoDeUsoEliminarPuntosDeAtencion;
import com.tesis.transito.dominio.casosdeuso.puntosdeatencion.CasoDeUsoGuardarPuntoDeAtencion;
import com.tesis.transito.dominio.modelos.PuntoDeAtencion;
import com.tesis.transito.persistencia.delegados.PuntoDeAtencionDelegado;
import com.tesis.transito.persistencia.modelos.DataPuntoDeAtencion;
import com.tesis.transito.persistencia.repositorios.PuntoDeAtencionRepositorio;
import com.tesis.transito.persistencia.utils.DataPuntoDeAtencionPuntoDeAtencionMapper;
import com.tesis.transito.persistencia.utils.PuntoDeAtencionDataPuntoDeAtencionMapper;
import com.tesis.transito.presentacion.controladores.PuntoDeAtencionControlador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PuntoDeAtencionControlador.class)
@EnableAutoConfiguration
@AutoConfigureDataMongo
@WithMockUser
public class PuntoDeAtencionTestIntegracion {

    @Autowired
    private WebTestClient webTestClient;

    @SpyBean
    private CasoDeUsoBuscarPuntosDeAtencion casoDeUsoBuscarPuntosDeAtencion;
    @SpyBean
    private CasoDeUsoGuardarPuntoDeAtencion casoDeUsoGuardarPuntoDeAtencion;
    @SpyBean
    private CasoDeUsoEliminarPuntosDeAtencion casoDeUsoEliminarPuntoDeAtencion;

    @MockBean
    private PuntoDeAtencionRepositorio repositorio;
    @SpyBean
    private PuntoDeAtencionDataPuntoDeAtencionMapper puntoDeAtencionDataPuntoDeAtencionMapper;
    @SpyBean
    private DataPuntoDeAtencionPuntoDeAtencionMapper dataPuntoDeAtencionPuntoDeAtencionMapper;

    @SpyBean
    private PuntoDeAtencionDelegado delegado;

    @Captor
    private ArgumentCaptor<DataPuntoDeAtencion> dataPuntoDeAtencionCaptor;
    @Captor
    private ArgumentCaptor<PuntoDeAtencion> puntoDeAtencionCaptor;

    @BeforeEach
    void setUp() {
        DataPuntoDeAtencion puntoDeAtencion = new DataPuntoDeAtencion(
                "test",
                "test",
                "test",
                "test"
        );

        DataPuntoDeAtencion puntoDeAtencionConId = new DataPuntoDeAtencion(
                "1",
                "test",
                "test",
                "test",
                "test"
        );

        List<DataPuntoDeAtencion> puntosDeAtencionConId = new ArrayList<>();
        puntosDeAtencionConId.add(puntoDeAtencionConId);
        List<DataPuntoDeAtencion> puntosDeAtencion = new ArrayList<>();
        puntosDeAtencion.add(puntoDeAtencion);

        when(repositorio.insert(puntosDeAtencion)).thenReturn(Flux.fromIterable(puntosDeAtencionConId));
        when(repositorio.save(puntoDeAtencionConId)).thenReturn(Mono.just(puntoDeAtencionConId));
        when(repositorio.save(puntoDeAtencion)).thenReturn(Mono.just(puntoDeAtencionConId));
        when(repositorio.findAll()).thenReturn(Flux.fromIterable(puntosDeAtencionConId));
        when(repositorio.findDataPuntoDeAtencionByNombre("test")).thenReturn(Flux.fromIterable(puntosDeAtencionConId));
        when(repositorio.delete(puntoDeAtencionConId)).thenReturn(Mono.empty());
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
        PuntoDeAtencion puntoDeAtencionConId = new PuntoDeAtencion(null, "test", "test", "test", "test");
        List<PuntoDeAtencion> puntosDeAtencion = new ArrayList<>();
        puntosDeAtencion.add(puntoDeAtencionConId);

        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/puntoDeAtencion")
                .body(Flux.fromIterable(puntosDeAtencion), PuntoDeAtencion.class)
                .exchange()
                .expectStatus().isOk();
    }
}
