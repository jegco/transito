package com.tesis.transito.persistencia.utils;

import com.tesis.transito.entidades.PreferenciasDeUsuario;
import com.tesis.transito.entidades.DataPreferenciasDeUsuario;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PreferenciasDeUsuarioDataPreferenciasDeUsuarioMapper implements
        Function<PreferenciasDeUsuario, DataPreferenciasDeUsuario> {

    private final AnimacionDataAnimacionMapper mapper;

    public PreferenciasDeUsuarioDataPreferenciasDeUsuarioMapper(AnimacionDataAnimacionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public DataPreferenciasDeUsuario apply(PreferenciasDeUsuario preferenciasDeUsuario) {
        if (preferenciasDeUsuario.getId() != null && !preferenciasDeUsuario.getId().isEmpty()) {
            return new DataPreferenciasDeUsuario(preferenciasDeUsuario.getId(),
                    preferenciasDeUsuario.getColorPrimario(), preferenciasDeUsuario.getColorSecundario(),
                    mapper.apply(preferenciasDeUsuario.getAnimacion()));
        }
        return new DataPreferenciasDeUsuario(
                preferenciasDeUsuario.getColorPrimario(), preferenciasDeUsuario.getColorSecundario(),
                mapper.apply(preferenciasDeUsuario.getAnimacion()));
    }
}
