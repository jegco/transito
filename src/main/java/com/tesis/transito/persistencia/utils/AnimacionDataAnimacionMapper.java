package com.tesis.transito.persistencia.utils;

import com.tesis.transito.dominio.modelos.Animacion;
import com.tesis.transito.persistencia.modelos.DataAnimacion;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AnimacionDataAnimacionMapper implements Function<Animacion, DataAnimacion> {

    @Override
    public DataAnimacion apply(Animacion animacion) {
        return new DataAnimacion(animacion.getId(),
                animacion.getNombre(),
                animacion.getTiempoDeEspera(),
                animacion.getTiempoDeAnimacion());
    }
}
