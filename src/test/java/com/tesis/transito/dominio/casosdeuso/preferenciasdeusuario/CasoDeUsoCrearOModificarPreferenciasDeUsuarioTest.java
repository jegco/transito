package com.tesis.transito.dominio.casosdeuso.preferenciasdeusuario;

import com.tesis.transito.entidades.Animacion;
import com.tesis.transito.entidades.PreferenciasDeUsuario;
import com.tesis.transito.persistencia.delegados.PreferenciasDeUsuarioDelegado;
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
class CasoDeUsoCrearOModificarPreferenciasDeUsuarioTest {

    @MockBean
    private PreferenciasDeUsuarioDelegado delegado;

    private CasoDeUsoCrearOModificarPreferenciasDeUsuario casoDeUso;

    private PreferenciasDeUsuario preferenciasDeUsuario;

    @BeforeEach
    void setUp() {
        preferenciasDeUsuario = new PreferenciasDeUsuario(
                "1",
                "test",
                "test",
                new Animacion()
        );
        when(delegado.actualizar(preferenciasDeUsuario)).thenReturn(Mono.just(preferenciasDeUsuario));
        casoDeUso = new CasoDeUsoCrearOModificarPreferenciasDeUsuario(delegado);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(preferenciasDeUsuario))
                .expectNextMatches(preferenciasDeUsuario -> preferenciasDeUsuario.getId().equals("1"))
                .expectComplete()
                .verify();
    }
}