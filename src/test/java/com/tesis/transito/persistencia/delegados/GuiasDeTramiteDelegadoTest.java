package com.tesis.transito.persistencia.delegados;

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

import java.util.ArrayList;
import java.util.List;

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
    private GuiaDeTramite guiaDeTramiteConId;
    private GuiaDeTramite guiaDeTramite;

    @Captor
    private ArgumentCaptor<DataGuiaDeTramite> dataGuiaDeTramiteCaptor;
    @Captor
    private ArgumentCaptor<GuiaDeTramite> guiaDeTramiteCaptor;


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
        when(repositorio.findAll()).thenReturn(Flux.fromIterable(guiasDeTramiteConId));
        when(repositorio.insert(guiasDeTramite)).thenReturn(Flux.fromIterable(guiasDeTramiteConId));
        when(repositorio.save(dataGuiaDeTramite)).thenReturn(Mono.just(dataGuiaDeTramiteConId));
        when(repositorio.delete(dataGuiaDeTramiteConId)).thenReturn(Mono.empty());

        delegado = new GuiasDeTramiteDelegado(repositorio, dataGuiaDeTramiteGuiaDeTramiteMapper, guiaDeTramiteDataGuiaDeTramiteMapper);
    }

    @Test
    void crear() {
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
        List<GuiaDeTramite> guiasDeTramite = new ArrayList<>();
        guiasDeTramite.add(guiaDeTramite);
        StepVerifier
                .create(delegado.crear(guiasDeTramite))
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
        StepVerifier
                .create(delegado.eliminar(guiaDeTramiteConId))
                .expectComplete()
                .verify();

        verify(guiaDeTramiteDataGuiaDeTramiteMapper).apply(guiaDeTramiteCaptor.capture());
        GuiaDeTramite value = guiaDeTramiteCaptor.getValue();
        Assertions.assertEquals("test nombre", value.getTitulo());
    }
}