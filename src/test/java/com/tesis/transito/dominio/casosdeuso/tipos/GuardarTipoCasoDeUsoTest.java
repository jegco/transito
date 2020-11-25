package com.tesis.transito.dominio.casosdeuso.tipos;

import com.tesis.transito.entidades.Documento;
import com.tesis.transito.entidades.Tipo;
import com.tesis.transito.persistencia.delegados.TiposDelegado;
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
class GuardarTipoCasoDeUsoTest {

    @MockBean
    private TiposDelegado delegado;

    private GuardarTipoCasoDeUso casoDeUso;

    private Tipo tipo;

    @BeforeEach
    void setUp() {
        tipo = new Tipo("1", "test", Documento.builder().nombre("test").id("1").build());
        when(delegado.crear(tipo)).thenReturn(Mono.just(tipo));
        casoDeUso = new GuardarTipoCasoDeUso(delegado);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(tipo))
                .expectNextMatches(tipo1 -> tipo1.getId().equals("1"))
                .expectComplete()
                .verify();
    }
}