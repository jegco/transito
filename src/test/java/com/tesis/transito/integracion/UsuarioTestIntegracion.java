package com.tesis.transito.integracion;

import com.tesis.transito.dominio.casosdeuso.params.UsuarioParams;
import com.tesis.transito.dominio.casosdeuso.usuarios.CasoDeUsoActivarUsuario;
import com.tesis.transito.dominio.casosdeuso.usuarios.CasoDeUsoEliminarUsuario;
import com.tesis.transito.dominio.casosdeuso.usuarios.CasoDeUsoLogin;
import com.tesis.transito.dominio.casosdeuso.usuarios.CasoDeUsoRegistrarUsuario;
import com.tesis.transito.entidades.Notificacion;
import com.tesis.transito.entidades.Usuario;
import com.tesis.transito.persistencia.delegados.UsuarioDelegado;
import com.tesis.transito.entidades.DataUsuario;
import com.tesis.transito.persistencia.repositorios.UsuarioRepositorio;
import com.tesis.transito.persistencia.utils.DataUsuarioUsuarioMapper;
import com.tesis.transito.persistencia.utils.UsuarioDataUsuarioMapper;
import com.tesis.transito.persistencia.utils.notificaciones.ServicioDeNotificacionPorCorreo;
import com.tesis.transito.presentacion.controladores.UsuarioControlador;
import com.tesis.transito.presentacion.modelos.AuthResponse;
import com.tesis.transito.presentacion.seguridad.JWTUtil;
import com.tesis.transito.presentacion.utils.mappers.UsuarioVistaUsuarioMapper;
import com.tesis.transito.presentacion.utils.mappers.VistaUsuarioUsuarioMapper;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UsuarioControlador.class)
@EnableAutoConfiguration
@AutoConfigureDataMongo
@WithMockUser
public class UsuarioTestIntegracion {

    @Autowired
    private WebTestClient webTestClient;

    @SpyBean
    private CasoDeUsoLogin casoDeUsoLogin;
    @SpyBean
    private CasoDeUsoRegistrarUsuario casoDeUsoRegistrarUsuario;
    @SpyBean
    private CasoDeUsoEliminarUsuario casoDeUsoEliminarUsuario;
    @SpyBean
    private CasoDeUsoActivarUsuario casoDeUsoActivarUsuario;
    @SpyBean
    private UsuarioVistaUsuarioMapper usuarioVistaUsuarioMapper;
    @SpyBean
    private VistaUsuarioUsuarioMapper vistaUsuarioUsuarioMapper;
    @SpyBean
    private JWTUtil jwtUtil;

    @MockBean
    private UsuarioRepositorio repositorio;
    @SpyBean
    private DataUsuarioUsuarioMapper dataUsuarioUsuarioMapper;
    @SpyBean
    private UsuarioDataUsuarioMapper usuarioDataUsuarioMapper;

    @SpyBean
    private UsuarioDelegado delegado;

    @MockBean
    ServicioDeNotificacionPorCorreo servicioDeNotificacionPorCorreo;

    @Captor
    private ArgumentCaptor<DataUsuario> dataUsuarioCaptor;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;

    @BeforeEach
    void setup() {
        DataUsuario dataUsuario = new DataUsuario(null,
                "jorge",
                "qwerty",
                "jegco13@gmail.com",
                "3192098062",
                "SUPER_ADMIN", false);

        DataUsuario dataUsuarioConId = new DataUsuario("1",
                "jorge",
                "qwerty",
                "jegco13@gmail.com",
                "3192098062",
                "SUPER_ADMIN", false);

        List<DataUsuario> dataUsuarios = new ArrayList<>();
        dataUsuarios.add(dataUsuario);

        when(repositorio.
                findDataUsuarioByNombreAndPasswordAndActive("jorge", "qwerty", true))
                .thenReturn(Flux.just(dataUsuario));
        when(repositorio.findAll()).thenReturn(Flux.just(dataUsuarioConId));
        when(repositorio.insert(dataUsuario)).thenReturn(Mono.just(dataUsuarioConId));
        when(repositorio.save(dataUsuarioConId)).thenReturn(Mono.just(dataUsuarioConId));
        when(repositorio.insert(dataUsuarios)).thenReturn(Flux.fromIterable(dataUsuarios));
        when(repositorio.findById("1")).thenReturn(Mono.just(dataUsuarioConId));
        when(repositorio.findDataUsuarioByActiveAndRol( true, "SUPER_ADMIN")).thenReturn(Flux.just(dataUsuarioConId));
        when(repositorio.
                findDataUsuarioByNombreAndPasswordAndActive(
                        dataUsuario.getNombre(),
                        dataUsuario.getPassword(),
                        false))
                .thenReturn(Flux.empty());
        when(repositorio.delete(dataUsuarioConId)).thenReturn(Mono.empty());

        Usuario usuario = new Usuario(null,
                "jorge",
                "qwerty",
                "jegco13@gmail.com",
                "3192098062",
                "SUPER_ADMIN", false);

        Usuario usuarioConId = new Usuario("1",
                "jorge",
                "qwerty",
                "jegco13@gmail.com",
                "3192098062",
                "SUPER_ADMIN", false);
        when(servicioDeNotificacionPorCorreo.enviarNotificacionAlUsuario(new Notificacion(usuarioConId, "registro exitoso", "registro autorizado por el administrador")))
                .thenReturn(Mono.just(true));

        when(servicioDeNotificacionPorCorreo.enviarNotificacionAlAdmin(new Notificacion(usuarioConId,
                usuario,
                "Confirmacion de registro de nuevo usuario",
                "por favor haga click en el mensaje para confirmar la entrada del nuevo usuario")))
                .thenReturn(Mono.just(usuario));
    }

    @Test
    void login() {
        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new UsuarioParams("jorge", "qwerty", null, true)), UsuarioParams.class)
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

        Usuario usuario = new Usuario(null,
                "jorge",
                "qwerty",
                "jegco13@gmail.com",
                "3192098062",
                "SUPER_ADMIN",
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
                "jegco13@gmail.com",
        "3192098062",
                "SUPER_ADMIN",
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
