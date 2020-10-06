package com.tesis.transito.dominio.casosdeuso.usuarios;

import com.tesis.transito.dominio.modelos.Notificacion;
import com.tesis.transito.dominio.modelos.Usuario;
import com.tesis.transito.persistencia.delegados.UsuarioDelegado;
import com.tesis.transito.persistencia.utils.notificaciones.ServicioDeNotificacionPorCorreo;
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
class CasoDeUsoActivarUsuarioTest {

    @MockBean
    private UsuarioDelegado delegado;

    private CasoDeUsoActivarUsuario casoDeUso;

    private Usuario usuario;

    @MockBean
    ServicioDeNotificacionPorCorreo servicioDeNotificacionPorCorreo;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder().id("1").nombreDeUsuario("test").password("test").build();
        when(delegado.actualizar(usuario)).thenReturn(Mono.just(usuario));
        when(servicioDeNotificacionPorCorreo.enviarNotificacionAlUsuario(new Notificacion(usuario, "registro exitoso", "registro autorizado por el administrador")))
                .thenReturn(Mono.just(true));
        casoDeUso = new CasoDeUsoActivarUsuario(delegado, servicioDeNotificacionPorCorreo);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(usuario))
                .expectNextMatches(active -> active)
                .expectComplete()
                .verify();
    }
}