package com.tesis.transito.dominio.casosdeuso.tipos;

import com.tesis.transito.dominio.modelos.Documento;
import com.tesis.transito.dominio.modelos.Tipo;
import com.tesis.transito.persistencia.delegados.TiposDelegado;
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
class EliminarTipoCasoDeUsoTest {

    @MockBean
    private TiposDelegado delegado;

    private EliminarTipoCasoDeUso casoDeUso;

    @MockBean
    private ServicioDeAlmacenamientoImpl servicioDeAlmacenamiento;

    Tipo tipo;

    @BeforeEach
    void setUp() {
        tipo = new Tipo("1", "test", Documento.builder().nombre("test").id("1").build());
        when(delegado.eliminar(tipo)).thenReturn(Mono.empty());
        when(servicioDeAlmacenamiento.eliminarArchivo("test")).thenReturn(Mono.just(true));
        casoDeUso = new EliminarTipoCasoDeUso(delegado, servicioDeAlmacenamiento);
    }

    @Test
    void construirCasoDeUso() {
        StepVerifier.
                create(casoDeUso.ejecutar(tipo))
                .expectComplete()
                .verify();
    }
}