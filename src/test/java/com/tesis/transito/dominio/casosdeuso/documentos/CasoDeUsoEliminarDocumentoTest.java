package com.tesis.transito.dominio.casosdeuso.documentos;

import com.tesis.transito.entidades.Documento;
import com.tesis.transito.persistencia.delegados.DocumentoDelegado;
import com.tesis.transito.persistencia.utils.archivos.ServicioDeAlmacenamientoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class CasoDeUsoEliminarDocumentoTest {

    @MockBean
    private DocumentoDelegado delegado;

    @MockBean
    private ServicioDeAlmacenamientoImpl servicioDeAlmacenamiento;

    private CasoDeUsoEliminarDocumento casoDeUso;

    @BeforeEach
    void setUp() {
        Documento documento = Documento.builder().id("1").archivo("test").rutaDeDescarga("test anexo").nombre("test anexo").build();
        when(delegado.eliminar(documento)).thenReturn(Mono.empty());
        when(servicioDeAlmacenamiento.eliminarArchivo("test")).thenReturn(Mono.just(true));
        casoDeUso = new CasoDeUsoEliminarDocumento(servicioDeAlmacenamiento, delegado);
    }

    @Test
    void construirCasoDeUso() {
        Documento documento = Documento.builder().id("1").archivo("test").rutaDeDescarga("test anexo").nombre("test anexo").build();
        StepVerifier.
                create(casoDeUso.ejecutar(documento))
                .expectComplete()
                .verify();
    }
}