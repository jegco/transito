package com.tesis.transito.persistencia.delegados;

import com.tesis.transito.dominio.modelos.PuntoDeAtencion;
import com.tesis.transito.persistencia.modelos.DataPuntoDeAtencion;
import com.tesis.transito.persistencia.repositorios.PuntoDeAtencionRepositorio;
import com.tesis.transito.persistencia.utils.DataPuntoDeAtencionPuntoDeAtencionMapper;
import com.tesis.transito.persistencia.utils.PuntoDeAtencionDataPuntoDeAtencionMapper;
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
class PuntoDeAtencionDelegadoTest {

    @MockBean
    private PuntoDeAtencionRepositorio repositorio;
    @SpyBean
    private PuntoDeAtencionDataPuntoDeAtencionMapper puntoDeAtencionDataPuntoDeAtencionMapper;
    @SpyBean
    private DataPuntoDeAtencionPuntoDeAtencionMapper dataPuntoDeAtencionPuntoDeAtencionMapper;

    private PuntoDeAtencionDelegado delegado;

    @Captor
    private ArgumentCaptor<DataPuntoDeAtencion> dataTipoCaptor;
    @Captor
    private ArgumentCaptor<PuntoDeAtencion> tipoCaptor;

    @BeforeEach
    void setUp() {
        DataPuntoDeAtencion puntoDeAtencion = new DataPuntoDeAtencion(
                "test",
                "test",
                "test",
                "test"
        );

        DataPuntoDeAtencion puntoDeAtencionConId = new DataPuntoDeAtencion(
                "1",
                "test",
                "test",
                "test",
                "test"
        );

        List<DataPuntoDeAtencion> puntosDeAtencionConId = new ArrayList<>();
        puntosDeAtencionConId.add(puntoDeAtencionConId);
        List<DataPuntoDeAtencion> puntosDeAtencion = new ArrayList<>();
        puntosDeAtencion.add(puntoDeAtencion);

        when(repositorio.insert(puntosDeAtencion)).thenReturn(Flux.fromIterable(puntosDeAtencionConId));
        when(repositorio.save(puntoDeAtencionConId)).thenReturn(Mono.just(puntoDeAtencionConId));
        when(repositorio.save(puntoDeAtencion)).thenReturn(Mono.just(puntoDeAtencionConId));
        when(repositorio.findAll()).thenReturn(Flux.fromIterable(puntosDeAtencionConId));
        when(repositorio.findDataPuntoDeAtencionByNombre("test")).thenReturn(Flux.fromIterable(puntosDeAtencionConId));
        when(repositorio.delete(puntoDeAtencionConId)).thenReturn(Mono.empty());

        delegado = new PuntoDeAtencionDelegado(repositorio, puntoDeAtencionDataPuntoDeAtencionMapper, dataPuntoDeAtencionPuntoDeAtencionMapper);
    }

    @Test
    void crear() {
        PuntoDeAtencion puntoDeAtencion = new PuntoDeAtencion(
                null,
                "test",
                "test",
                "test",
                "test"
        );
        StepVerifier.
                create(delegado.crear(puntoDeAtencion))
                .expectNextMatches(punto -> punto.getNombre().equals("test"))
                .expectComplete()
                .verify();

        verify(puntoDeAtencionDataPuntoDeAtencionMapper).apply(tipoCaptor.capture());
        verify(dataPuntoDeAtencionPuntoDeAtencionMapper).apply(dataTipoCaptor.capture());
        DataPuntoDeAtencion value = dataTipoCaptor.getValue();
        Assertions.assertEquals("test", value.getNombre());
    }

    @Test
    void crearVarios() {
        PuntoDeAtencion puntoDeAtencion = new PuntoDeAtencion(
                null,
                "test",
                "test",
                "test",
                "test"
        );

        List<PuntoDeAtencion> puntos = new ArrayList<>();
        puntos.add(puntoDeAtencion);
        StepVerifier.
                create(delegado.crear(puntos))
                .expectNextMatches(punto -> punto.getNombre().equals("test"))
                .expectComplete()
                .verify();

        verify(puntoDeAtencionDataPuntoDeAtencionMapper).apply(tipoCaptor.capture());
        verify(dataPuntoDeAtencionPuntoDeAtencionMapper).apply(dataTipoCaptor.capture());
        DataPuntoDeAtencion value = dataTipoCaptor.getValue();
        Assertions.assertEquals("test", value.getNombre());
    }

    @Test
    void actualizar() {
        PuntoDeAtencion puntoDeAtencion = new PuntoDeAtencion(
                "1",
                "test",
                "test",
                "test",
                "test"
        );
        StepVerifier.
                create(delegado.actualizar(puntoDeAtencion))
                .expectNextMatches(punto -> punto.getNombre().equals("test"))
                .expectComplete()
                .verify();

        verify(puntoDeAtencionDataPuntoDeAtencionMapper).apply(tipoCaptor.capture());
        verify(dataPuntoDeAtencionPuntoDeAtencionMapper).apply(dataTipoCaptor.capture());
        DataPuntoDeAtencion value = dataTipoCaptor.getValue();
        Assertions.assertEquals("test", value.getNombre());
    }

    @Test
    void buscar() {
        StepVerifier.create(delegado.buscar("test"))
                .expectNextMatches(tipo -> tipo.getId().equals("1"))
                .expectComplete()
                .verify();

        verify(dataPuntoDeAtencionPuntoDeAtencionMapper).apply(dataTipoCaptor.capture());
        DataPuntoDeAtencion punto = dataTipoCaptor.getValue();
        Assertions.assertEquals("test", punto.getNombre());
    }

    @Test
    void eliminar() {
        PuntoDeAtencion puntoDeAtencion = new PuntoDeAtencion(
                "1",
                "test",
                "test",
                "test",
                "test"
        );

        StepVerifier.
                create(delegado.eliminar(puntoDeAtencion))
                .expectComplete()
                .verify();

        verify(puntoDeAtencionDataPuntoDeAtencionMapper).apply(tipoCaptor.capture());
        PuntoDeAtencion punto = tipoCaptor.getValue();
        Assertions.assertEquals("test", punto.getNombre());
    }
}