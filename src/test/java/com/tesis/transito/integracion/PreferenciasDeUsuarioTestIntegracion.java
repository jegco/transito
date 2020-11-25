package com.tesis.transito.integracion;

import com.tesis.transito.dominio.casosdeuso.preferenciasdeusuario.CasoDeUsoBuscarPreferenciasDeUsuario;
import com.tesis.transito.dominio.casosdeuso.preferenciasdeusuario.CasoDeUsoCrearOModificarPreferenciasDeUsuario;
import com.tesis.transito.entidades.Animacion;
import com.tesis.transito.entidades.PreferenciasDeUsuario;
import com.tesis.transito.persistencia.delegados.PreferenciasDeUsuarioDelegado;
import com.tesis.transito.entidades.DataAnimacion;
import com.tesis.transito.entidades.DataPreferenciasDeUsuario;
import com.tesis.transito.persistencia.repositorios.PreferenciasDeUsuarioRepositorio;
import com.tesis.transito.persistencia.utils.AnimacionDataAnimacionMapper;
import com.tesis.transito.persistencia.utils.DataAnimacionAnimacionMapper;
import com.tesis.transito.persistencia.utils.DataPreferenciasDeUsuarioPreferenciasDeUsuarioMapper;
import com.tesis.transito.persistencia.utils.PreferenciasDeUsuarioDataPreferenciasDeUsuarioMapper;
import com.tesis.transito.presentacion.controladores.PreferenciasDeUsuarioControlador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
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
@WebFluxTest(controllers = PreferenciasDeUsuarioControlador.class)
@EnableAutoConfiguration
@AutoConfigureDataMongo
@WithMockUser
public class PreferenciasDeUsuarioTestIntegracion {

    @Autowired
    private WebTestClient webTestClient;

    @SpyBean
    private CasoDeUsoBuscarPreferenciasDeUsuario casoDeUsoBuscarPreferenciasDeUsuario;
    @SpyBean
    private CasoDeUsoCrearOModificarPreferenciasDeUsuario casoDeUsoCrearOModificarPreferenciasDeUsuario;
    @SpyBean
    private PreferenciasDeUsuarioDelegado delegado;
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

    @Captor
    private ArgumentCaptor<DataPreferenciasDeUsuario> dataPreferenciasCaptor;
    @Captor
    private ArgumentCaptor<PreferenciasDeUsuario> preferenciasCaptor;


    @BeforeEach
    void setUp() {
        DataPreferenciasDeUsuario preferenciasDeUsuario = new DataPreferenciasDeUsuario("1", "test", "test", new DataAnimacion());
        List<DataPreferenciasDeUsuario> preferencias = new ArrayList<>();
        preferencias.add(preferenciasDeUsuario);

        when(repositorio.findAll()).thenReturn(Flux.fromIterable(preferencias));
        when(repositorio.save(preferenciasDeUsuario)).thenReturn(Mono.just(preferenciasDeUsuario));
    }

    @Test
    void buscarPreferenciasDeUsuario() {
        webTestClient
                .mutateWith(csrf())
                .get()
                .uri("/preferencias")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void crearOModificarPreferenciasDeUsuario() {
        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/preferencias")
                .body(Mono.just(new PreferenciasDeUsuario("1", "test", "test", new Animacion())), PreferenciasDeUsuario.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(PreferenciasDeUsuario.class)
                .consumeWith(preferenciasEntityExchangeResult ->
                        assertEquals("1", preferenciasEntityExchangeResult
                                .getResponseBody()
                                .getId()));

        verify(menuDataMenuMapper).apply(preferenciasCaptor.capture());
        verify(dataMenuMenuMapper).apply(dataPreferenciasCaptor.capture());
        DataPreferenciasDeUsuario value = dataPreferenciasCaptor.getValue();
        assertEquals("test", value.getColorPrimario());
        verify(delegado).actualizar(preferenciasCaptor.capture());
        assertEquals("test", preferenciasCaptor.getValue().getColorPrimario());
    }
}
