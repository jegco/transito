package com.tesis.transito.dominio.casosdeuso.guiasdetramite;

import com.sun.tools.javac.util.List;
import com.tesis.transito.dominio.modelos.*;
import com.tesis.transito.persistencia.delegados.GuiasDeTramiteDelegado;
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
class CasoDeUsoCrearGuiasDeTramiteTest {

    @MockBean
    private GuiasDeTramiteDelegado delegado;

    private CasoDeUsoCrearGuiasDeTramite casoDeUso;

    GuiaDeTramite guiaDeTramite;

    @BeforeEach
    void setUp() {
        Documento documento = Documento.builder().id("1").rutaDeDescarga("test anexo").nombre("test anexo").build();
        List<PuntoDeAtencion> puntosDeAtencion = List.of(new PuntoDeAtencion(
                "1",
                "test",
                "test",
                "test",
                "test"
        ));
        Tipo tipo = new Tipo("1", "test", documento);
        Paso paso = new Paso("test titulo", "test descripcion", documento);

        guiaDeTramite = new GuiaDeTramite(
                "1",
                "test nombre",
                "test descripcion",
                documento,
                List.of(paso),
                "test soporte legal",
                puntosDeAtencion,
                tipo
        );
        when(delegado.crear(guiaDeTramite)).thenReturn(Mono.just(guiaDeTramite));
        casoDeUso = new CasoDeUsoCrearGuiasDeTramite(delegado);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(guiaDeTramite))
                .expectNextMatches(guiaDeTramite -> guiaDeTramite.getId().equals("1"))
                .expectComplete()
                .verify();
    }
}