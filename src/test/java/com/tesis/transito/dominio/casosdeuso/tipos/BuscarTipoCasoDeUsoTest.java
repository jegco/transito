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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class BuscarTipoCasoDeUsoTest {

    @MockBean
    private TiposDelegado delegado;

    private BuscarTipoCasoDeUso casoDeUso;

    @BeforeEach
    void setUp() {
        Tipo tipo = new Tipo("1", "test", Documento.builder().id("1").build());
        List<Tipo> tipos = new ArrayList<>();
        tipos.add(tipo);
        when(delegado.buscar(null)).thenReturn(Flux.fromIterable(tipos));
        casoDeUso = new BuscarTipoCasoDeUso(delegado);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(null))
                .expectNextMatches(tipo -> tipo.getId().equals("1"))
                .expectComplete()
                .verify();
    }
}