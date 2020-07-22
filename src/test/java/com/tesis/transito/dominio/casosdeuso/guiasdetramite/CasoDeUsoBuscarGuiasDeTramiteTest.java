package com.tesis.transito.dominio.casosdeuso.guiasdetramite;

import com.sun.tools.javac.util.List;
import com.tesis.transito.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.transito.dominio.modelos.*;
import com.tesis.transito.persistencia.delegados.GuiasDeTramiteDelegado;
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
class CasoDeUsoBuscarGuiasDeTramiteTest {

    @MockBean
    private GuiasDeTramiteDelegado delegado;

    private CasoDeUsoBuscarGuiasDeTramite casoDeUso;

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

        GuiaDeTramite guiaDeTramite = new GuiaDeTramite(
                "1",
                "test nombre",
                "test descripcion",
                documento,
                List.of(paso),
                "test soporte legal",
                puntosDeAtencion,
                tipo
        );
        when(delegado.buscar(new GuiaDeTramiteParams("test"))).thenReturn(Flux.fromIterable(List.of(guiaDeTramite)));
        casoDeUso = new CasoDeUsoBuscarGuiasDeTramite(delegado);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(new GuiaDeTramiteParams("test")))
                .expectNextMatches(guiaDeTramite -> guiaDeTramite.getId().equals("1"))
                .expectComplete()
                .verify();
    }
}