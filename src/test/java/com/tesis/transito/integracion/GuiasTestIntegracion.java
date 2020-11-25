package com.tesis.transito.integracion;

import com.tesis.transito.dominio.casosdeuso.guiasdetramite.CasoDeUsoBuscarGuiasDeTramite;
import com.tesis.transito.dominio.casosdeuso.guiasdetramite.CasoDeUsoCrearGuiasDeTramite;
import com.tesis.transito.dominio.casosdeuso.guiasdetramite.CasoDeUsoEliminarGuia;
import com.tesis.transito.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.transito.dominio.utils.exceptions.GuideNotFoundException;
import com.tesis.transito.entidades.*;
import com.tesis.transito.persistencia.delegados.GuiasDeTramiteDelegado;
import com.tesis.transito.persistencia.repositorios.DocumentoRepositorio;
import com.tesis.transito.persistencia.repositorios.GuiasDeTramitesRepositorio;
import com.tesis.transito.persistencia.repositorios.PuntoDeAtencionRepositorio;
import com.tesis.transito.persistencia.repositorios.TiposRepositorio;
import com.tesis.transito.persistencia.utils.*;
import com.tesis.transito.presentacion.controladores.GuiasDeTramiteControlador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = GuiasDeTramiteControlador.class)
@EnableAutoConfiguration
@AutoConfigureDataMongo
@AutoConfigureWebTestClient(timeout = "30000")
@WithMockUser
public class GuiasTestIntegracion {

    @Autowired
    private WebTestClient webTestClient;

    @SpyBean
    private CasoDeUsoBuscarGuiasDeTramite casoDeUsoBuscarGuiaDeTramite;
    @SpyBean
    private CasoDeUsoCrearGuiasDeTramite casoDeUsoCrearGuiasDetramite;
    @SpyBean
    private CasoDeUsoEliminarGuia casoDeUsoEliminarGuiaDeTramite;

    @SpyBean
    private GuiasDeTramiteDelegado delegado;

    @MockBean
    private GuiasDeTramitesRepositorio repositorio;
    @MockBean
    private DocumentoRepositorio documentoRepositorio;
    @MockBean
    private TiposRepositorio tiposRepositorio;
    @MockBean
    private PuntoDeAtencionRepositorio puntoDeAtencionRepositorio;
    @SpyBean
    private DataGuiaDeTramiteGuiaDeTramiteMapper dataGuiaDeTramiteGuiaDeTramiteMapper;
    @SpyBean
    private GuiaDeTramiteDataGuiaDeTramiteMapper guiaDeTramiteDataGuiaDeTramiteMapper;
    @SpyBean
    private DataDocumentoDocumentoMapper dataDocumentoDocumentoMapper;
    @SpyBean
    private DataPasoPasoMapper dataPasoPasoMapper;
    @SpyBean
    private DataPuntoDeAtencionPuntoDeAtencionMapper dataPuntoDeAtencionPuntoDeAtencionMapper;
    @SpyBean
    private DataTipoTipoMapper dataTipoTipoMapper;
    @SpyBean
    private PasoDataPasoMapper pasoDataPasoMapper;

    @Captor
    private ArgumentCaptor<GuiaDeTramiteParams> guiaDeTramiteParamsCaptor;

    @Captor
    private ArgumentCaptor<GuiaDeTramite> guiaDeTramiteCaptor;


    private GuiaDeTramite guiaDeTramite;
    private GuiaDeTramite guiaDeTramiteConId;

    @BeforeEach
    void setUp() {
        List<DataPuntoDeAtencion> dataPuntosDeAtencion = new ArrayList<>();
        dataPuntosDeAtencion.add(new DataPuntoDeAtencion(
                "1",
                "test",
                "test",
                "test",
                "test"
        ));
        DataTipo dataTipo = new DataTipo("1", "test", "1");
        DataDocumento dataDocumento = DataDocumento.builder().id("1").rutaDeDescarga("test anexo").nombre("test anexo").build();
        DataPaso dataPaso = new DataPaso("test titulo", "test descripcion", "1");
        List<DataPaso> dataPasos = new ArrayList<>();
        dataPasos.add(dataPaso);
        List<String> dataPuntosDeAtencionId = new ArrayList<>();
        dataPuntosDeAtencionId.add("1");
        DataGuiaDeTramite dataGuiaDeTramiteConId = new DataGuiaDeTramite(
                "1",
                "test nombre",
                "test descripcion",
                "1",
                dataTipo.getId(),
                "test soporte legal",
                dataPuntosDeAtencionId,
                dataPasos
        );
        DataGuiaDeTramite dataGuiaDeTramite = new DataGuiaDeTramite(
                null,
                "test nombre",
                "test descripcion",
                "1",
                dataTipo.getId(),
                "test soporte legal",
                dataPuntosDeAtencionId,
                dataPasos
        );
        List<DataGuiaDeTramite> guiasDeTramiteConId = new ArrayList<>();
        guiasDeTramiteConId.add(dataGuiaDeTramiteConId);
        List<DataGuiaDeTramite> guiasDeTramite = new ArrayList<>();
        guiasDeTramite.add(dataGuiaDeTramite);

        Documento documento = Documento.builder().id("1").rutaDeDescarga("test anexo").nombre("test anexo").build();
        List<PuntoDeAtencion> puntosDeAtencion = new ArrayList<>();
        puntosDeAtencion.add(new PuntoDeAtencion(
                "1",
                "test",
                "test",
                "test",
                "test"
        ));
        Tipo tipo = new Tipo("1", "test", documento);
        Paso paso = new Paso("test titulo", "test descripcion", documento);
        List<Paso> pasos = new ArrayList<>();
        pasos.add(paso);

        guiaDeTramite = new GuiaDeTramite(
                null,
                "test nombre",
                "test descripcion",
                documento,
                pasos,
                "test soporte legal",
                puntosDeAtencion,
                tipo
        );

        guiaDeTramiteConId = new GuiaDeTramite(
                "1",
                "test nombre",
                "test descripcion",
                documento,
                pasos,
                "test soporte legal",
                puntosDeAtencion,
                tipo
        );

        when(tiposRepositorio.findById("1")).thenReturn(Mono.just(dataTipo));
        when(puntoDeAtencionRepositorio.findAllById(dataPuntosDeAtencionId)).thenReturn(Flux.fromIterable(dataPuntosDeAtencion));
        when(documentoRepositorio.findById("1")).thenReturn(Mono.just(dataDocumento));
        when(repositorio.findAllByTipoContains("test")).thenReturn(Flux.fromIterable(guiasDeTramiteConId));
        when(repositorio.findAllByTipoContains("failtest")).thenReturn(Flux.empty());
        when(repositorio.findAll()).thenReturn(Flux.fromIterable(guiasDeTramiteConId));
        when(repositorio.insert(guiasDeTramite)).thenReturn(Flux.fromIterable(guiasDeTramiteConId));
        when(repositorio.save(dataGuiaDeTramite)).thenReturn(Mono.just(dataGuiaDeTramiteConId));
        when(repositorio.delete(dataGuiaDeTramiteConId)).thenReturn(Mono.empty());
    }

    @Test
    void buscarGuiasDeTramitePorTipo() {

        webTestClient
                .mutateWith(csrf())
                .get()
                .uri("/guias/test")
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .consumeWith(guiaDeTramiteEntityExchangeResult ->
                        assertEquals(1, guiaDeTramiteEntityExchangeResult
                                .getResponseBody()
                                .size()));

        verify(casoDeUsoBuscarGuiaDeTramite).ejecutar(guiaDeTramiteParamsCaptor.capture());
        assertEquals("test", guiaDeTramiteParamsCaptor.getValue().getTipo());
        verify(delegado).buscar(guiaDeTramiteParamsCaptor.capture());
        assertEquals("test", guiaDeTramiteParamsCaptor.getValue().getTipo());
    }

    @Test
    void buscarGuiasDeTramite() {

        webTestClient
                .mutateWith(csrf())
                .get()
                .uri("/guias")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void crearGuiaDeTramite() {
        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/guias")
                .body(Mono.just(guiaDeTramite), GuiaDeTramite.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(GuiaDeTramite.class)
                .consumeWith(guiaDeTramiteEntityExchangeResult ->
                        assertEquals(guiaDeTramiteConId.getId(), guiaDeTramiteEntityExchangeResult.getResponseBody().getId()));

        verify(casoDeUsoCrearGuiasDetramite).ejecutar(guiaDeTramiteCaptor.capture());
        assertEquals(guiaDeTramite.getTitulo(), guiaDeTramiteCaptor.getValue().getTitulo());
    }

    @Test
    void buscarGuiasDeTramitePorTituloTipoYDescripcion() {
        webTestClient
                .mutateWith(csrf())
                .get()
                .uri("/guias/test")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void noGuiaEncontrada() {
        webTestClient
                .mutateWith(csrf())
                .get()
                .uri("/guias/failtest")
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(GuideNotFoundException.class);
    }
}
