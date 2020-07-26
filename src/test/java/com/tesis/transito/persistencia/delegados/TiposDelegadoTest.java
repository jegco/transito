package com.tesis.transito.persistencia.delegados;

import com.tesis.transito.dominio.modelos.Documento;
import com.tesis.transito.dominio.modelos.Tipo;
import com.tesis.transito.persistencia.modelos.DataDocumento;
import com.tesis.transito.persistencia.modelos.DataTipo;
import com.tesis.transito.persistencia.repositorios.DocumentoRepositorio;
import com.tesis.transito.persistencia.repositorios.TiposRepositorio;
import com.tesis.transito.persistencia.utils.DataDocumentoDocumentoMapper;
import com.tesis.transito.persistencia.utils.DataTipoTipoMapper;
import com.tesis.transito.persistencia.utils.DocumentoDataDocumentoMapper;
import com.tesis.transito.persistencia.utils.TipoDataTipoMapper;
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
class TiposDelegadoTest {

    @MockBean
    private TiposRepositorio repositorio;

    @MockBean
    private DocumentoRepositorio documentoRepositorio;

    @SpyBean
    private DataTipoTipoMapper dataTipoTipoMapper;
    @SpyBean
    private TipoDataTipoMapper tipoDataTipoMapper;
    @SpyBean
    private DataDocumentoDocumentoMapper dataDocumentoDocumentoMapper;
    @SpyBean
    private DocumentoDataDocumentoMapper documentoDataDocumentoMapper;

    private TiposDelegado delegado;

    @Captor
    private ArgumentCaptor<DataTipo> dataTipoCaptor;
    @Captor
    private ArgumentCaptor<Tipo> tipoCaptor;

    @BeforeEach
    void setUp() {

        DataTipo dataTipo = new DataTipo(null, "test", "1");
        DataTipo dataTipoConId = new DataTipo("1", "test", "1");
        DataDocumento documento = DataDocumento.builder().id("1").build();

        List<DataTipo> dataTipos = new ArrayList<>();
        dataTipos.add(dataTipo);
        List<DataTipo> dataTiposConId = new ArrayList<>();
        dataTiposConId.add(dataTipoConId);

        when(repositorio.insert(dataTipo)).thenReturn(Mono.just(dataTipo));
        when(repositorio.save(dataTipo)).thenReturn(Mono.just(dataTipo));
        when(repositorio.save(dataTipoConId)).thenReturn(Mono.just(dataTipoConId));
        when(repositorio.insert(dataTipos)).thenReturn(Flux.fromIterable(dataTipos));
        when(repositorio.findById("1")).thenReturn(Mono.just(dataTipoConId));
        when(repositorio.findDataTipoByNombre("test")).thenReturn(Flux.fromIterable(dataTiposConId));
        when(repositorio.findAll()).thenReturn(Flux.fromIterable(dataTiposConId));
        when(repositorio.delete(dataTipoConId)).thenReturn(Mono.empty());
        when(documentoRepositorio.findById("1")).thenReturn(Mono.just(documento));

        delegado = new TiposDelegado(repositorio, dataTipoTipoMapper, tipoDataTipoMapper);
    }

    @Test
    void crear() {
        Documento documento = new Documento();
        documento.setId("1");
        StepVerifier.
                create(delegado.crear(new Tipo(null, "test", documento)))
                .expectNextMatches(tipo -> tipo.getNombre().equals("test"))
                .expectComplete()
                .verify();

        verify(tipoDataTipoMapper).apply(tipoCaptor.capture());
        verify(dataTipoTipoMapper).apply(dataTipoCaptor.capture());
        DataTipo tipo = dataTipoCaptor.getValue();
        Assertions.assertEquals("test", tipo.getNombre());
    }

    @Test
    void crearVarios() {
        Documento documento = new Documento();
        documento.setId("1");
        List<Tipo> tipos = new ArrayList<>();
        tipos.add(new Tipo(null, "test", documento));
        StepVerifier.
                create(delegado.crear(tipos))
                .expectNextMatches(tipo -> tipo.getNombre().equals("test"))
                .expectComplete()
                .verify();

        verify(tipoDataTipoMapper).apply(tipoCaptor.capture());
        verify(dataTipoTipoMapper).apply(dataTipoCaptor.capture());
        DataTipo tipo = dataTipoCaptor.getValue();
        Assertions.assertEquals("test", tipo.getNombre());
    }

    @Test
    void actualizar() {
        Documento documento = new Documento();
        documento.setId("1");

        StepVerifier.
                create(delegado.actualizar(new Tipo("1", "test", documento)))
                .expectNextMatches(tipo -> tipo.getNombre().equals("test"))
                .expectComplete()
                .verify();

        verify(tipoDataTipoMapper).apply(tipoCaptor.capture());
        verify(dataTipoTipoMapper).apply(dataTipoCaptor.capture());
        DataTipo tipo = dataTipoCaptor.getValue();
        Assertions.assertEquals("test", tipo.getNombre());
    }

    @Test
    void buscar() {
        StepVerifier.create(delegado.buscar("test"))
                .expectNextMatches(tipo -> tipo.getId().equals("1"))
                .expectComplete()
                .verify();

        verify(dataTipoTipoMapper).apply(dataTipoCaptor.capture());
        DataTipo tipo = dataTipoCaptor.getValue();
        Assertions.assertEquals("test", tipo.getNombre());
    }

    @Test
    void eliminar() {
        Documento documento = new Documento();
        documento.setId("1");

        StepVerifier.
                create(delegado.eliminar(new Tipo("1", "test", documento)))
                .expectComplete()
                .verify();

        verify(tipoDataTipoMapper).apply(tipoCaptor.capture());
        Tipo tipo = tipoCaptor.getValue();
        Assertions.assertEquals("test", tipo.getNombre());
    }
}