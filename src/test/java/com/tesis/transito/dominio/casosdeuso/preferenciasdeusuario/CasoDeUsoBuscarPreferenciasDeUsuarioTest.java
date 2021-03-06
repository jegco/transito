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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class CasoDeUsoBuscarPreferenciasDeUsuarioTest {

    @MockBean
    private PreferenciasDeUsuarioDelegado delegado;

    private CasoDeUsoBuscarPreferenciasDeUsuario casoDeUso;

    @BeforeEach
    void setUp() {
        PreferenciasDeUsuario preferenciaDeUsuario = new PreferenciasDeUsuario(
                "1",
                "test",
                "test",
                new Animacion()
        );
        List<PreferenciasDeUsuario> preferenciasDeUsiario = new ArrayList<>();
        preferenciasDeUsiario.add(preferenciaDeUsuario);
        when(delegado.buscar(null)).thenReturn(Flux.fromIterable(preferenciasDeUsiario));
        casoDeUso = new CasoDeUsoBuscarPreferenciasDeUsuario(delegado);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(null))
                .expectNextMatches(preferenciasDeUsuario -> preferenciasDeUsuario.getId().equals("1"))
                .expectComplete()
                .verify();
    }
}