package com.tesis.transito.presentacion.controladores;

import com.sun.tools.javac.util.List;
import com.tesis.transito.dominio.casosdeuso.tipos.BuscarTipoCasoDeUso;
import com.tesis.transito.dominio.casosdeuso.tipos.EliminarTipoCasoDeUso;
import com.tesis.transito.dominio.casosdeuso.tipos.GuardarTipoCasoDeUso;
import com.tesis.transito.dominio.modelos.Documento;
import com.tesis.transito.dominio.modelos.Tipo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TiposControlador.class)
@EnableAutoConfiguration
@AutoConfigureDataMongo
@WithMockUser
class TiposControladorTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BuscarTipoCasoDeUso buscarTiposCasoDeUso;

    @MockBean
    private GuardarTipoCasoDeUso guardarTiposCasoDeUso;

    @MockBean
    private EliminarTipoCasoDeUso eliminarTiposCasoDeUso;

    @BeforeEach
    void setup() {

        Tipo tipo = new Tipo(null, "test", new Documento());
        Tipo tipoConId = new Tipo("1", "test", new Documento());

        List<Tipo> tipos = List.of(tipoConId);

        when(buscarTiposCasoDeUso.ejecutar(null)).thenReturn(Flux.fromIterable(tipos));
        when(buscarTiposCasoDeUso.ejecutar("jorge")).thenReturn(Flux.fromIterable(tipos));
        when(guardarTiposCasoDeUso.ejecutar(tipo)).thenReturn(Mono.just(tipoConId));
    }

    @Test
    void buscarTipos() {
        webTestClient
                .mutateWith(csrf())
                .get()
                .uri("/tipos/jorge")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testBuscarTipos() {
        webTestClient
                .mutateWith(csrf())
                .get()
                .uri("/tipos")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void guardarTipo() {
        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/tipos")
                .body(Mono.just(new Tipo(null, "test", new Documento())), Tipo.class)
                .exchange()
                .expectStatus().isOk();
    }
}