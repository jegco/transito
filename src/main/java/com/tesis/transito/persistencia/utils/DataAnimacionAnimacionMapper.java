package com.tesis.transito.persistencia.utils;

import com.tesis.transito.entidades.Animacion;
import com.tesis.transito.entidades.DataAnimacion;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DataAnimacionAnimacionMapper implements Function<DataAnimacion, Animacion> {

    @Override
    public Animacion apply(DataAnimacion dataAnimacion) {
        return new Animacion(dataAnimacion.getId(),
                dataAnimacion.getNombre(),
                dataAnimacion.getTiempoDeEspera(),
                dataAnimacion.getTiempoAnimacion());
    }
}
