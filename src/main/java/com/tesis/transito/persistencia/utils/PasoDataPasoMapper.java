package com.tesis.transito.persistencia.utils;

import com.tesis.transito.entidades.Paso;
import com.tesis.transito.entidades.DataPaso;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PasoDataPasoMapper implements Function<Paso, DataPaso> {

    @Override
    public DataPaso apply(Paso paso) {
        return new DataPaso(paso.getTitulo(),
                paso.getDescripcion(),
                paso.getAnexo() != null ? paso.getAnexo().getId(): null);
    }
}
