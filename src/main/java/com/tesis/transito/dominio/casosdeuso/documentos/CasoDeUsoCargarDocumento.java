package com.tesis.transito.dominio.casosdeuso.documentos;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.utils.ServicioDeAlmacenamiento;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CasoDeUsoCargarDocumento extends CasoDeUsoImpl<String, Resource> {

    private final ServicioDeAlmacenamiento servicioDeAlmacenamiento;

    public CasoDeUsoCargarDocumento(ServicioDeAlmacenamiento servicioDeAlmacenamiento) {
        this.servicioDeAlmacenamiento = servicioDeAlmacenamiento;
    }

    @Override
    protected Flux<Resource> construirCasoDeUso(String documento) {
        return Flux.from(servicioDeAlmacenamiento.cargarDocumento(documento));
    }
}
