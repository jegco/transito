package com.tesis.transito.persistencia.delegados;

import com.sun.tools.javac.util.List;
import com.tesis.transito.dominio.modelos.Animacion;
import com.tesis.transito.dominio.modelos.PreferenciasDeUsuario;
import com.tesis.transito.persistencia.modelos.DataAnimacion;
import com.tesis.transito.persistencia.modelos.DataPreferenciasDeUsuario;
import com.tesis.transito.persistencia.repositorios.PreferenciasDeUsuarioRepositorio;
import com.tesis.transito.persistencia.utils.AnimacionDataAnimacionMapper;
import com.tesis.transito.persistencia.utils.DataAnimacionAnimacionMapper;
import com.tesis.transito.persistencia.utils.DataPreferenciasDeUsuarioPreferenciasDeUsuarioMapper;
import com.tesis.transito.persistencia.utils.PreferenciasDeUsuarioDataPreferenciasDeUsuarioMapper;
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
class PreferenciasDeUsuarioDelegadoTest {

    @MockBean
    private PreferenciasDeUsuarioRepositorio repositorio;
    @SpyBean
    private PreferenciasDeUsuarioDataPreferenciasDeUsuarioMapper menuDataMenuMapper;
    @SpyBean
    private DataPreferenciasDeUsuarioPreferenciasDeUsuarioMapper dataMenuMenuMapper;
    @SpyBean
    private AnimacionDataAnimacionMapper animacionDataAnimacionMapper;
    @SpyBean
    private DataAnimacionAnimacionMapper dataAnimacionAnimacionMapper;

    private PreferenciasDeUsuarioDelegado delegado;

    @Captor
    private ArgumentCaptor<DataPreferenciasDeUsuario> dataTipoCaptor;
    @Captor
    private ArgumentCaptor<PreferenciasDeUsuario> tipoCaptor;

    @BeforeEach
    void setUp() {
        DataPreferenciasDeUsuario preferenciaDeUsuario = new DataPreferenciasDeUsuario(
                "test",
                "test",
                new DataAnimacion(null, null, 0, 0)
        );

        DataPreferenciasDeUsuario preferenciaDeUsuarioConId = new DataPreferenciasDeUsuario(
                "1",
                "test",
                "test",
                new DataAnimacion(null, null, 0, 0)
        );

        List<DataPreferenciasDeUsuario> preferenciasDeUsuario = List.of(preferenciaDeUsuario);
        List<DataPreferenciasDeUsuario> preferenciasDeUsuarioConId = List.of(preferenciaDeUsuarioConId);

        when(repositorio.findAll()).thenReturn(Flux.fromIterable(preferenciasDeUsuarioConId));
        when(repositorio.insert(preferenciaDeUsuario)).thenReturn(Mono.just(preferenciaDeUsuarioConId));
        when(repositorio.insert(preferenciasDeUsuario)).thenReturn(Flux.fromIterable(preferenciasDeUsuarioConId));
        when(repositorio.save(preferenciaDeUsuario)).thenReturn(Mono.just(preferenciaDeUsuarioConId));
        when(repositorio.delete(preferenciaDeUsuarioConId)).thenReturn(Mono.empty());

        delegado = new PreferenciasDeUsuarioDelegado(repositorio, menuDataMenuMapper, dataMenuMenuMapper);
    }

    @Test
    void crear() {
        PreferenciasDeUsuario preferenciaDeUsuario = new PreferenciasDeUsuario(
                null,
                "test",
                "test",
                new Animacion()
        );

        StepVerifier.
                create(delegado.crear(preferenciaDeUsuario))
                .expectNextMatches(preferencia -> preferencia.getColorPrimario().equals("test"))
                .expectComplete()
                .verify();

        verify(menuDataMenuMapper).apply(tipoCaptor.capture());
        verify(dataMenuMenuMapper).apply(dataTipoCaptor.capture());
        DataPreferenciasDeUsuario value = dataTipoCaptor.getValue();
        Assertions.assertEquals("test", value.getColorPrimario());
    }

    @Test
    void crearVarios() {
        PreferenciasDeUsuario preferenciaDeUsuario = new PreferenciasDeUsuario(
                null,
                "test",
                "test",
                new Animacion()
        );

        List<PreferenciasDeUsuario> prefenciasDeUsuario = List.of(preferenciaDeUsuario);

        StepVerifier.
                create(delegado.crear(prefenciasDeUsuario))
                .expectNextMatches(preferencia -> preferencia.getColorPrimario().equals("test"))
                .expectComplete()
                .verify();

        verify(menuDataMenuMapper).apply(tipoCaptor.capture());
        verify(dataMenuMenuMapper).apply(dataTipoCaptor.capture());
        DataPreferenciasDeUsuario value = dataTipoCaptor.getValue();
        Assertions.assertEquals("test", value.getColorPrimario());
    }

    @Test
    void actualizar() {
        PreferenciasDeUsuario preferenciaDeUsuario = new PreferenciasDeUsuario(
                null,
                "test",
                "test",
                new Animacion()
        );

        StepVerifier.
                create(delegado.actualizar(preferenciaDeUsuario))
                .expectNextMatches(preferencia -> preferencia.getColorPrimario().equals("test"))
                .expectComplete()
                .verify();

        verify(menuDataMenuMapper).apply(tipoCaptor.capture());
        verify(dataMenuMenuMapper).apply(dataTipoCaptor.capture());
        DataPreferenciasDeUsuario value = dataTipoCaptor.getValue();
        Assertions.assertEquals("test", value.getColorPrimario());
    }

    @Test
    void buscar() {
        StepVerifier.create(delegado.buscar("test"))
                .expectNextMatches(tipo -> tipo.getId().equals("1"))
                .expectComplete()
                .verify();

        verify(dataMenuMenuMapper).apply(dataTipoCaptor.capture());
        DataPreferenciasDeUsuario value = dataTipoCaptor.getValue();
        Assertions.assertEquals("test", value.getColorPrimario());
    }

    @Test
    void eliminar() {
        PreferenciasDeUsuario preferenciaDeUsuario = new PreferenciasDeUsuario(
                "1",
                "test",
                "test",
                new Animacion()
        );

        StepVerifier.
                create(delegado.eliminar(preferenciaDeUsuario))
                .expectComplete()
                .verify();

        verify(menuDataMenuMapper).apply(tipoCaptor.capture());
        PreferenciasDeUsuario value = tipoCaptor.getValue();
        Assertions.assertEquals("test", value.getColorPrimario());
    }
}