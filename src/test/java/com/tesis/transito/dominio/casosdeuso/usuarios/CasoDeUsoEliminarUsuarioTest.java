package com.tesis.transito.dominio.casosdeuso.usuarios;

import com.tesis.transito.entidades.Usuario;
import com.tesis.transito.persistencia.delegados.UsuarioDelegado;
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
class CasoDeUsoEliminarUsuarioTest {

    @MockBean
    private UsuarioDelegado delegado;

    private CasoDeUsoEliminarUsuario casoDeUso;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder().id("1").nombreDeUsuario("jorge").build();
        when(delegado.eliminar(usuario)).thenReturn(Mono.empty());
        casoDeUso = new CasoDeUsoEliminarUsuario(delegado);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(usuario))
                .expectComplete()
                .verify();
    }
}