package com.tesis.transito.dominio.casosdeuso.documentos;

import com.tesis.transito.entidades.Documento;
import com.tesis.transito.persistencia.delegados.DocumentoDelegado;
import com.tesis.transito.persistencia.utils.archivos.ServicioDeAlmacenamientoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.FileNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class CasoDeUsoGuardarDocumentoTest {

    @MockBean
    private DocumentoDelegado delegado;

    @MockBean
    private ServicioDeAlmacenamientoImpl servicioDeAlmacenamiento;

    @SpyBean
    Environment env;

    private CasoDeUsoGuardarDocumento casoDeUso;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        Documento documento = Documento.builder().id("1").rutaDeDescarga("test anexo").nombre("test anexo").build();
        when(delegado.crear(documento)).thenReturn(Mono.just(documento));
        when(servicioDeAlmacenamiento.guardarDocumento(any())).thenReturn(Mono.just(documento));
        casoDeUso = new CasoDeUsoGuardarDocumento(servicioDeAlmacenamiento, delegado, env);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(null))
                .expectNextMatches(documento -> documento.getId().equals("1"))
                .expectComplete()
                .verify();
    }
}