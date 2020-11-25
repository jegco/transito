package com.tesis.transito.dominio.casosdeuso.documentos;

import com.tesis.transito.persistencia.utils.archivos.ServicioDeAlmacenamientoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class CasoDeUsoCargarDocumentoTest {

    @Mock
    Resource resource;

    @MockBean
    private ServicioDeAlmacenamientoImpl servicioDeAlmacenamiento;

    private CasoDeUsoCargarDocumento casoDeUso;

    @BeforeEach
    void setUp() {
        when(servicioDeAlmacenamiento.cargarDocumento("test")).thenReturn(Mono.just(resource));
        casoDeUso = new CasoDeUsoCargarDocumento(servicioDeAlmacenamiento);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar("test"))
                .expectNext(resource)
                .expectComplete()
                .verify();
    }
}