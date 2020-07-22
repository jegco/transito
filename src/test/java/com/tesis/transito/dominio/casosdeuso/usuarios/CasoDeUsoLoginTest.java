package com.tesis.transito.dominio.casosdeuso.usuarios;

import com.tesis.transito.dominio.casosdeuso.params.UsuarioParams;
import com.tesis.transito.dominio.modelos.Usuario;
import com.tesis.transito.persistencia.delegados.UsuarioDelegado;
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
class CasoDeUsoLoginTest {

    @MockBean
    private UsuarioDelegado delegado;

    private CasoDeUsoLogin casoDeUso;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder().id("1").nombreDeUsuario("test").contraseña("test").build();
        when(delegado.buscar(new UsuarioParams("test", "test"))).thenReturn(Flux.just(usuario));
        casoDeUso = new CasoDeUsoLogin(delegado);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(new UsuarioParams("test", "test")))
                .expectNextMatches(usuario1 -> usuario1.getNombreDeUsuario().equals("test"))
                .expectComplete()
                .verify();
    }
}