package com.tesis.transito.presentacion.controladores;

import com.tesis.transito.dominio.casosdeuso.params.UsuarioParams;
import com.tesis.transito.dominio.casosdeuso.usuarios.CasoDeUsoActivarUsuario;
import com.tesis.transito.dominio.casosdeuso.usuarios.CasoDeUsoEliminarUsuario;
import com.tesis.transito.dominio.casosdeuso.usuarios.CasoDeUsoLogin;
import com.tesis.transito.dominio.casosdeuso.usuarios.CasoDeUsoRegistrarUsuario;
import com.tesis.transito.entidades.Usuario;
import com.tesis.transito.presentacion.modelos.AuthResponse;
import com.tesis.transito.presentacion.seguridad.JWTUtil;
import com.tesis.transito.presentacion.utils.mappers.UsuarioVistaUsuarioMapper;
import com.tesis.transito.presentacion.utils.mappers.VistaUsuarioUsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UsuarioControlador.class)
@EnableAutoConfiguration
@AutoConfigureDataMongo
@WithMockUser
class UsuarioControladorTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CasoDeUsoLogin casoDeUsoLogin;
    @MockBean
    private CasoDeUsoRegistrarUsuario casoDeUsoRegistrarUsuario;
    @MockBean
    private CasoDeUsoEliminarUsuario casoDeUsoEliminarUsuario;
    @MockBean
    private CasoDeUsoActivarUsuario casoDeUsoActivarUsuario;
    @SpyBean
    private UsuarioVistaUsuarioMapper usuarioVistaUsuarioMapper;
    @SpyBean
    private VistaUsuarioUsuarioMapper vistaUsuarioUsuarioMapper;
    @SpyBean
    private JWTUtil jwtUtil;

    @BeforeEach
    void setup() {
        when(casoDeUsoLogin.
                ejecutar(new UsuarioParams("jorge", "qwerty", null, false)))
                .thenReturn(Mono.just(Usuario.builder().nombreDeUsuario("jorge").password("querty").id("1").build()));

        when(casoDeUsoRegistrarUsuario.
                ejecutar(new Usuario("1",
                        "jorge",
                        "qwerty",
                        "j@q.com",
                        "12345",
                        "ADMIN",
                        false)))
                .thenReturn(Mono.just(new Usuario("1",
                        "jorge",
                        "qwerty",
                        "j@q.com",
                        "12345",
                        "ADMIN",
                        false)));

        when(casoDeUsoActivarUsuario.
                ejecutar(new Usuario("1",
                        "jorge",
                        null,
                        "j@q.com",
                        "12345",
                        "ADMIN",
                        false)))
                .thenReturn(Mono.just(true));

        when(casoDeUsoLogin.
                ejecutar(null))
                .thenReturn(Flux.just(new Usuario("1",
                        "jorge",
                        null,
                        "j@q.com",
                        "12345",
                        "ADMIN",
                        false)));
    }

    @Test
    void login() {
        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new UsuarioParams("jorge", "qwerty")), UsuarioParams.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthResponse.class).consumeWith(
                usuarioExchangeResult -> {
                    assert usuarioExchangeResult.getResponseBody() != null;
                }
        );
    }

    @Test
    void registrarUsuario() {

        Usuario usuario = new Usuario("1",
                "jorge",
                "qwerty",
                "j@q.com",
                "12345",
                "ADMIN",
                false);

        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(usuario))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void eliminarUsuario() {
    }

    @Test
    void buscarUsuarios() {
        webTestClient
                .mutateWith(csrf())
                .get()
                .uri("/usuarios")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void actviarUsuario() {
        Usuario usuario = new Usuario("1",
                "jorge",
                "qwerty",
                "j@q.com",
                "12345",
                "ADMIN",
                false);

        webTestClient
                .mutateWith(csrf())
                .put()
                .uri("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(usuario))
                .exchange()
                .expectStatus().isOk();
    }
}