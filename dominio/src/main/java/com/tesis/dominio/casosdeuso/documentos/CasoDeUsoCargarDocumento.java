package com.tesis.dominio.casosdeuso.documentos;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.utils.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CasoDeUsoCargarDocumento extends CasoDeUsoImpl<String, Resource> {

    private final StorageService servicioDeAlmacenamiento;

    public CasoDeUsoCargarDocumento(StorageService servicioDeAlmacenamiento) {
        this.servicioDeAlmacenamiento = servicioDeAlmacenamiento;
    }

    @Override
    protected Flux<Resource> construirCasoDeUso(String documento) {
        return Flux.from(servicioDeAlmacenamiento.cargarDocumento(documento));
    }
}
