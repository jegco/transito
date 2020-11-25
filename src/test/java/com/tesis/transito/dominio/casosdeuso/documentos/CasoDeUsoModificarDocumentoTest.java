package com.tesis.transito.dominio.casosdeuso.documentos;

import com.tesis.transito.dominio.casosdeuso.params.ActualizarArchivoParam;
import com.tesis.transito.entidades.Documento;
import com.tesis.transito.persistencia.delegados.DocumentoDelegado;
import com.tesis.transito.persistencia.utils.archivos.ServicioDeAlmacenamientoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class CasoDeUsoModificarDocumentoTest {

    @MockBean
    private DocumentoDelegado delegado;

    @Mock
    FilePart filePart;

    @MockBean
    private ServicioDeAlmacenamientoImpl servicioDeAlmacenamiento;

    private CasoDeUsoModificarDocumento casoDeUso;

    @BeforeEach
    void setUp() {
        Documento documento = Documento.builder().id("1").rutaDeDescarga("test anexo").nombre("test anexo").build();
        when(delegado.actualizar(documento)).thenReturn(Mono.just(documento));
        when(servicioDeAlmacenamiento.guardarDocumento(any())).thenReturn(Mono.just(documento));
        casoDeUso = new CasoDeUsoModificarDocumento(servicioDeAlmacenamiento, delegado);
    }

    @Test
    void construirCasoDeUso() {
        Documento documento = Documento.builder().id("1").rutaDeDescarga("test anexo").nombre("test anexo").build();
        StepVerifier.
                create(casoDeUso.ejecutar(new ActualizarArchivoParam(documento, filePart)))
                .expectNextMatches(doc -> doc.getId().equals("1"))
                .expectComplete()
                .verify();
    }
}