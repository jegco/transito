package com.tesis.transito.persistencia.delegados;

import com.sun.tools.javac.util.List;
import com.tesis.transito.dominio.modelos.*;
import com.tesis.transito.persistencia.modelos.*;
import com.tesis.transito.persistencia.repositorios.DocumentoRepositorio;
import com.tesis.transito.persistencia.repositorios.GuiasDeTramitesRepositorio;
import com.tesis.transito.persistencia.repositorios.PuntoDeAtencionRepositorio;
import com.tesis.transito.persistencia.repositorios.TiposRepositorio;
import com.tesis.transito.persistencia.utils.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class GuiasDeTramiteDelegadoTest {

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

    private GuiasDeTramiteDelegado delegado;

    @Captor
    private ArgumentCaptor<DataGuiaDeTramite> dataGuiaDeTramiteCaptor;
    @Captor
    private ArgumentCaptor<GuiaDeTramite> guiaDeTramiteCaptor;


    @BeforeEach
    void setUp() {
        List<DataPuntoDeAtencion> puntosDeAtencion = List.of(new DataPuntoDeAtencion(
                "1",
                "test",
                "test",
                "test",
                "test"
        ));
        DataTipo tipo = new DataTipo("1", "test", "1");
        DataDocumento documento = DataDocumento.builder().id("1").rutaDeDescarga("test anexo").nombre("test anexo").build();
        DataPaso paso = new DataPaso("test titulo", "test descripcion", "1");
        DataGuiaDeTramite guiaDeTramiteConId = new DataGuiaDeTramite(
                "1",
                "test nombre",
                "test descripcion",
                "1",
                tipo.getId(),
                "test soporte legal",
                List.of("1"),
                List.of(paso)
        );
        DataGuiaDeTramite guiaDeTramite = new DataGuiaDeTramite(
                null,
                "test nombre",
                "test descripcion",
                "1",
                tipo.getId(),
                "test soporte legal",
                List.of("1"),
                List.of(paso)
        );

        when(tiposRepositorio.findById("1")).thenReturn(Mono.just(tipo));
        when(puntoDeAtencionRepositorio.findAllById(List.of("1"))).thenReturn(Flux.fromIterable(puntosDeAtencion));
        when(documentoRepositorio.findById("1")).thenReturn(Mono.just(documento));
        when(repositorio.findAll()).thenReturn(Flux.fromIterable(List.of(guiaDeTramiteConId)));
        when(repositorio.insert(List.of(guiaDeTramite))).thenReturn(Flux.fromIterable(List.of(guiaDeTramiteConId)));
        when(repositorio.save(guiaDeTramite)).thenReturn(Mono.just(guiaDeTramiteConId));
        when(repositorio.delete(guiaDeTramiteConId)).thenReturn(Mono.empty());

        delegado = new GuiasDeTramiteDelegado(repositorio, dataGuiaDeTramiteGuiaDeTramiteMapper, guiaDeTramiteDataGuiaDeTramiteMapper);
    }

    @Test
    void crear() {
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
                null,
                "test nombre",
                "test descripcion",
                documento,
                List.of(paso),
                "test soporte legal",
                puntosDeAtencion,
                tipo
        );

        StepVerifier
                .create(delegado.crear(guiaDeTramite))
                .expectNextMatches(guia -> guia.getId().equals("1"))
                .expectComplete()
                .verify();

        verify(guiaDeTramiteDataGuiaDeTramiteMapper).apply(guiaDeTramiteCaptor.capture());
        verify(dataGuiaDeTramiteGuiaDeTramiteMapper).apply(dataGuiaDeTramiteCaptor.capture());
        DataGuiaDeTramite value = dataGuiaDeTramiteCaptor.getValue();
        Assertions.assertEquals("test nombre", value.getTitulo());
    }

    @Test
    void crearVarios() {
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
                null,
                "test nombre",
                "test descripcion",
                documento,
                List.of(paso),
                "test soporte legal",
                puntosDeAtencion,
                tipo
        );

        StepVerifier
                .create(delegado.crear(List.of(guiaDeTramite)))
                .expectNextMatches(guia -> guia.getId().equals("1"))
                .expectComplete()
                .verify();

        verify(guiaDeTramiteDataGuiaDeTramiteMapper).apply(guiaDeTramiteCaptor.capture());
        verify(dataGuiaDeTramiteGuiaDeTramiteMapper).apply(dataGuiaDeTramiteCaptor.capture());
        DataGuiaDeTramite value = dataGuiaDeTramiteCaptor.getValue();
        Assertions.assertEquals("test nombre", value.getTitulo());
    }

    @Test
    void actualizar() {
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
                null,
                "test nombre",
                "test descripcion",
                documento,
                List.of(paso),
                "test soporte legal",
                puntosDeAtencion,
                tipo
        );

        StepVerifier
                .create(delegado.actualizar(guiaDeTramite))
                .expectNextMatches(guia -> guia.getId().equals("1"))
                .expectComplete()
                .verify();

        verify(guiaDeTramiteDataGuiaDeTramiteMapper).apply(guiaDeTramiteCaptor.capture());
        verify(dataGuiaDeTramiteGuiaDeTramiteMapper).apply(dataGuiaDeTramiteCaptor.capture());
        DataGuiaDeTramite value = dataGuiaDeTramiteCaptor.getValue();
        Assertions.assertEquals("test nombre", value.getTitulo());
    }

    @Test
    void buscar() {
        StepVerifier.create(delegado.buscar(null))
                .expectNextMatches(guia -> guia.getId().equals("1"))
                .expectComplete()
                .verify();

        verify(dataGuiaDeTramiteGuiaDeTramiteMapper).apply(dataGuiaDeTramiteCaptor.capture());
        DataGuiaDeTramite value = dataGuiaDeTramiteCaptor.getValue();
        Assertions.assertEquals("test nombre", value.getTitulo());
    }

    @Test
    void eliminar() {
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

        StepVerifier
                .create(delegado.eliminar(guiaDeTramite))
                .expectComplete()
                .verify();

        verify(guiaDeTramiteDataGuiaDeTramiteMapper).apply(guiaDeTramiteCaptor.capture());
        GuiaDeTramite value = guiaDeTramiteCaptor.getValue();
        Assertions.assertEquals("test nombre", value.getTitulo());
    }
}