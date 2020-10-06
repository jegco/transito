package com.tesis.transito.integracion;

import com.tesis.transito.dominio.casosdeuso.tipos.BuscarTipoCasoDeUso;
import com.tesis.transito.dominio.casosdeuso.tipos.EliminarTipoCasoDeUso;
import com.tesis.transito.dominio.casosdeuso.tipos.GuardarTipoCasoDeUso;
import com.tesis.transito.dominio.modelos.Documento;
import com.tesis.transito.dominio.modelos.Tipo;
import com.tesis.transito.persistencia.delegados.TiposDelegado;
import com.tesis.transito.persistencia.modelos.DataDocumento;
import com.tesis.transito.persistencia.modelos.DataTipo;
import com.tesis.transito.persistencia.repositorios.DocumentoRepositorio;
import com.tesis.transito.persistencia.repositorios.TiposRepositorio;
import com.tesis.transito.persistencia.utils.DataDocumentoDocumentoMapper;
import com.tesis.transito.persistencia.utils.DataTipoTipoMapper;
import com.tesis.transito.persistencia.utils.DocumentoDataDocumentoMapper;
import com.tesis.transito.persistencia.utils.TipoDataTipoMapper;
import com.tesis.transito.persistencia.utils.archivos.ServicioDeAlmacenamientoImpl;
import com.tesis.transito.presentacion.controladores.TiposControlador;
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

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TiposControlador.class)
@EnableAutoConfiguration
@AutoConfigureDataMongo
@WithMockUser
public class TipoTestIntegracion {

    @Autowired
    private WebTestClient webTestClient;

    @SpyBean
    private BuscarTipoCasoDeUso buscarTiposCasoDeUso;

    @SpyBean
    private GuardarTipoCasoDeUso guardarTiposCasoDeUso;

    @SpyBean
    private EliminarTipoCasoDeUso eliminarTiposCasoDeUso;

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

    @MockBean
    private ServicioDeAlmacenamientoImpl servicioDeAlmacenamiento;

    @SpyBean
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
                .body(Mono.just(new Tipo(null, "test", Documento
                        .builder()
                        .id("1")
                        .build())), Tipo.class)
                .exchange()
                .expectStatus().isOk();
    }
}
