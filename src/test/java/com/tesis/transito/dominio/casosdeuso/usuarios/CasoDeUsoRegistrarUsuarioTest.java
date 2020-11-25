package com.tesis.transito.dominio.casosdeuso.usuarios;

import com.tesis.transito.dominio.casosdeuso.params.UsuarioParams;
import com.tesis.transito.entidades.Notificacion;
import com.tesis.transito.entidades.Usuario;
import com.tesis.transito.persistencia.delegados.UsuarioDelegado;
import com.tesis.transito.persistencia.utils.notificaciones.ServicioDeNotificacionPorCorreo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class CasoDeUsoRegistrarUsuarioTest {

    @MockBean
    private UsuarioDelegado delegado;

    private CasoDeUsoRegistrarUsuario casoDeUso;

    private Usuario usuario;

    @MockBean
    ServicioDeNotificacionPorCorreo servicioDeNotificacionPorCorreo;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder().id("1").nombreDeUsuario("test").password("test").build();
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        when(delegado.crear(usuario)).thenReturn(Mono.just(usuario));
        when(delegado.buscar(new UsuarioParams("test", "test"))).thenReturn(Flux.empty());
        when(delegado.buscar(new UsuarioParams("SUPER_ADMIN", true))).thenReturn(Flux.fromIterable(usuarios));
        when(servicioDeNotificacionPorCorreo.enviarNotificacionAlAdmin(new Notificacion(usuario,
                usuario,
                "Confirmacion de registro de nuevo usuario",
                "por favor haga click en el mensaje para confirmar la entrada del nuevo usuario")))
                .thenReturn(Mono.just(usuario));
        casoDeUso = new CasoDeUsoRegistrarUsuario(delegado, servicioDeNotificacionPorCorreo);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(usuario))
                .expectNextMatches(usuario1 -> usuario1.getId().equals("1"))
                .expectComplete()
                .verify();
    }
}