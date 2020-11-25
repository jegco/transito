package com.tesis.transito.persistencia.delegados;

import com.tesis.transito.entidades.Documento;
import com.tesis.transito.entidades.DataDocumento;
import com.tesis.transito.persistencia.repositorios.DocumentoRepositorio;
import com.tesis.transito.persistencia.utils.DataDocumentoDocumentoMapper;
import com.tesis.transito.persistencia.utils.DocumentoDataDocumentoMapper;
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
class DocumentoDelegadoTest {

    @MockBean
    private DocumentoRepositorio repositorio;
    @SpyBean
    private DataDocumentoDocumentoMapper dataDocumentoDocumentoMapper;
    @SpyBean
    private DocumentoDataDocumentoMapper documentoDataDocumentoMapper;

    private DocumentoDelegado delegado;

    @Captor
    private ArgumentCaptor<DataDocumento> dataDocumentoCaptor;
    @Captor
    private ArgumentCaptor<Documento> documentoCaptor;

    @BeforeEach
    void setUp() {
        DataDocumento documento = DataDocumento.builder().rutaDeDescarga("test anexo").nombre("test anexo").build();
        DataDocumento documentoConId = DataDocumento.builder().id("1").rutaDeDescarga("test anexo").nombre("test anexo").build();
        List<DataDocumento> documentos = new ArrayList<>();
        documentos.add(documento);
        List<DataDocumento> documentosConId = new ArrayList<>();
        documentosConId.add(documentoConId);
        when(repositorio.findDataDocumentoByNombreContains("test")).thenReturn(Flux.fromIterable(documentosConId));
        when(repositorio.findAll()).thenReturn(Flux.fromIterable(documentosConId));
        when(repositorio.insert(documento)).thenReturn(Mono.just(documentoConId));
        when(repositorio.insert(documentos)).thenReturn(Flux.fromIterable(documentosConId));
        when(repositorio.save(documentoConId)).thenReturn(Mono.just(documentoConId));
        when(repositorio.delete(documentoConId)).thenReturn(Mono.empty());

        delegado = new DocumentoDelegado(repositorio, dataDocumentoDocumentoMapper, documentoDataDocumentoMapper);
    }

    @Test
    void crear() {
        Documento documento = Documento.builder().rutaDeDescarga("test anexo").nombre("test anexo").build();

        StepVerifier.
                create(delegado.crear(documento))
                .expectNextMatches(doc -> doc.getId().equals("1"))
                .expectComplete()
                .verify();

        verify(documentoDataDocumentoMapper).apply(documentoCaptor.capture());
        verify(dataDocumentoDocumentoMapper).apply(dataDocumentoCaptor.capture());
        DataDocumento value = dataDocumentoCaptor.getValue();
        Assertions.assertEquals("test anexo", value.getNombre());
    }

    @Test
    void crearVarios() {
        Documento documento = Documento.builder().rutaDeDescarga("test anexo").nombre("test anexo").build();
        List<Documento> documentos = new ArrayList<Documento>();
        documentos.add(documento);

        StepVerifier.
                create(delegado.crear(documentos))
                .expectNextMatches(doc -> doc.getId().equals("1"))
                .expectComplete()
                .verify();

        verify(documentoDataDocumentoMapper).apply(documentoCaptor.capture());
        verify(dataDocumentoDocumentoMapper).apply(dataDocumentoCaptor.capture());
        DataDocumento value = dataDocumentoCaptor.getValue();
        Assertions.assertEquals("test anexo", value.getNombre());
    }

    @Test
    void actualizar() {
        Documento documento = Documento.builder().id("1").rutaDeDescarga("test anexo").nombre("test anexo").build();

        StepVerifier.
                create(delegado.actualizar(documento))
                .expectNextMatches(doc -> doc.getId().equals("1"))
                .expectComplete()
                .verify();

        verify(documentoDataDocumentoMapper).apply(documentoCaptor.capture());
        verify(dataDocumentoDocumentoMapper).apply(dataDocumentoCaptor.capture());
        DataDocumento value = dataDocumentoCaptor.getValue();
        Assertions.assertEquals("test anexo", value.getNombre());
    }

    @Test
    void buscar() {
        StepVerifier.create(delegado.buscar(null))
                .expectNextMatches(tipo -> tipo.getId().equals("1"))
                .expectComplete()
                .verify();

        verify(dataDocumentoDocumentoMapper).apply(dataDocumentoCaptor.capture());
        DataDocumento value = dataDocumentoCaptor.getValue();
        Assertions.assertEquals("test anexo", value.getNombre());

        StepVerifier.create(delegado.buscar("test"))
                .expectNextMatches(tipo -> tipo.getId().equals("1"))
                .expectComplete()
                .verify();

    }

    @Test
    void eliminar() {
        Documento documento = Documento.builder().id("1").rutaDeDescarga("test anexo").nombre("test anexo").build();

        StepVerifier.
                create(delegado.eliminar(documento))
                .expectComplete()
                .verify();

        verify(documentoDataDocumentoMapper).apply(documentoCaptor.capture());
        Documento value = documentoCaptor.getValue();
        Assertions.assertEquals("test anexo", value.getNombre());
    }
}