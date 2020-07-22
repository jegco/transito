package com.tesis.transito.presentacion.controladores;

import com.sun.tools.javac.util.List;
import com.tesis.transito.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.transito.dominio.casosdeuso.guiasdetramite.CasoDeUsoBuscarGuiasDeTramite;
import com.tesis.transito.dominio.casosdeuso.guiasdetramite.CasoDeUsoCrearGuiasDeTramite;
import com.tesis.transito.dominio.casosdeuso.guiasdetramite.CasoDeUsoEliminarGuia;
import com.tesis.transito.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.transito.dominio.modelos.*;
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

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = GuiasDeTramiteControlador.class)
@EnableAutoConfiguration
@AutoConfigureDataMongo
@WithMockUser
class GuiasDeTramiteControladorTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CasoDeUsoBuscarGuiasDeTramite casoDeUsoBuscarGuiaDeTramite;
    @MockBean
    private CasoDeUsoCrearGuiasDeTramite casoDeUsoCrearGuiasDetramite;
    @MockBean
    private CasoDeUsoEliminarGuia casoDeUsoEliminarGuiaDeTramite;

    GuiaDeTramite guiaDeTramite;
    GuiaDeTramite guiaDeTramiteConId;

    @BeforeEach
    void setUp() {
        Documento documento = Documento.builder().id("1").rutaDeDescarga("test anexo").nombre("test anexo").build();
        List<PuntoDeAtencion> puntosDeAtencion = List.of(new PuntoDeAtencion(
                "1",
                "test",
                "test",
                "test",
                "test"
        ));
        Tipo tipo = new Tipo("1", "test", documento);
        Paso paso = new Paso("test titulo", "test descripcion", documento);

        guiaDeTramite = new GuiaDeTramite(
                null,
                "test nombre",
                "test descripcion",
                documento,
                List.of(paso),
                "test soporte legal",
                puntosDeAtencion,
                tipo
        );

        guiaDeTramiteConId = new GuiaDeTramite(
                "1",
                "test nombre",
                "test descripcion",
                documento,
                List.of(paso),
                "test soporte legal",
                puntosDeAtencion,
                tipo
        );

        when(casoDeUsoBuscarGuiaDeTramite.ejecutar(null)).thenReturn(Flux.just(guiaDeTramiteConId));
        GuiaDeTramiteParams guiaDeTramiteParams = new GuiaDeTramiteParams("test");
        when(casoDeUsoBuscarGuiaDeTramite.ejecutar(guiaDeTramiteParams)).thenReturn(Flux.just(guiaDeTramiteConId));
        guiaDeTramiteParams.setTitulo("test");
        when(casoDeUsoBuscarGuiaDeTramite.ejecutar(guiaDeTramiteParams)).thenReturn(Flux.just(guiaDeTramiteConId));
        guiaDeTramiteParams.setDescripcion("test");
        when(casoDeUsoBuscarGuiaDeTramite.ejecutar(guiaDeTramiteParams)).thenReturn(Flux.just(guiaDeTramiteConId));
        when(casoDeUsoCrearGuiasDetramite.ejecutar(guiaDeTramite)).thenReturn(Mono.just(guiaDeTramiteConId));
        when(casoDeUsoEliminarGuiaDeTramite.ejecutar(guiaDeTramiteConId)).thenReturn(Mono.empty());
    }

    @Test
    void buscarGuiasDeTramitePorTipo() {
        webTestClient
                .mutateWith(csrf())
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/guias")
                        .queryParam("tipo", "test")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void buscarGuiasDeTramite() {
        webTestClient
                .mutateWith(csrf())
                .get()
                .uri("/guias")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void buscarGuiasDeTramitePorTituloTipoYDescripcion() {
        webTestClient
                .mutateWith(csrf())
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/guias")
                        .queryParam("titulo", "test")
                        .queryParam("tipo", "test")
                        .queryParam("descripcion", "test")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void buscarGuiasDeTramitePorTitulo() {
        webTestClient
                .mutateWith(csrf())
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/guias")
                        .queryParam("titulo", "test")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void crearGuiaDeTramite() {
        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/guias")
                .body(Mono.just(guiaDeTramite), GuiaDeTramite.class)
                .exchange()
                .expectStatus().isOk();
    }
}