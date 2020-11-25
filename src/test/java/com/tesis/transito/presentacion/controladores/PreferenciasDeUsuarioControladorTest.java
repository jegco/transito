package com.tesis.transito.presentacion.controladores;

import com.tesis.transito.dominio.casosdeuso.preferenciasdeusuario.CasoDeUsoBuscarPreferenciasDeUsuario;
import com.tesis.transito.dominio.casosdeuso.preferenciasdeusuario.CasoDeUsoCrearOModificarPreferenciasDeUsuario;
import com.tesis.transito.entidades.Animacion;
import com.tesis.transito.entidades.PreferenciasDeUsuario;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PreferenciasDeUsuarioControlador.class)
@EnableAutoConfiguration
@AutoConfigureDataMongo
@WithMockUser
class PreferenciasDeUsuarioControladorTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CasoDeUsoBuscarPreferenciasDeUsuario casoDeUsoBuscarPreferenciasDeUsuario;
    @MockBean
    private CasoDeUsoCrearOModificarPreferenciasDeUsuario casoDeUsoCrearOModificarPreferenciasDeUsuario;


    @BeforeEach
    void setUp() {
        PreferenciasDeUsuario preferenciasDeUsuario = new PreferenciasDeUsuario("1", "test", "test", new Animacion());
        List<PreferenciasDeUsuario> preferencias = new ArrayList<>();
        preferencias.add(preferenciasDeUsuario);

        when(casoDeUsoBuscarPreferenciasDeUsuario.ejecutar(null)).thenReturn(Flux.fromIterable(preferencias));
        when(casoDeUsoCrearOModificarPreferenciasDeUsuario.ejecutar(preferenciasDeUsuario)).thenReturn(Mono.just(preferenciasDeUsuario));
    }

    @Test
    void buscarPreferenciasDeUsuario() {
        webTestClient
                .mutateWith(csrf())
                .get()
                .uri("/preferencias")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void crearOModificarPreferenciasDeUsuario() {
        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/preferencias")
                .body(Mono.just(new PreferenciasDeUsuario("1", "test", "test", new Animacion())), PreferenciasDeUsuario.class)
                .exchange()
                .expectStatus().isOk();
    }
}