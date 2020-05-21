package com.tesis.transito.persistencia.utils;

import com.tesis.transito.dominio.modelos.PreferenciasDeUsuario;
import com.tesis.transito.persistencia.modelos.DataPreferenciasDeUsuario;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DataPreferenciasDeUsuarioPreferenciasDeUsuarioMapper
        implements Function<DataPreferenciasDeUsuario, PreferenciasDeUsuario> {

    private final DataAnimacionAnimacionMapper mapper;

    public DataPreferenciasDeUsuarioPreferenciasDeUsuarioMapper(DataAnimacionAnimacionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PreferenciasDeUsuario apply(DataPreferenciasDeUsuario dataPreferenciasDeUsuario) {
        return new PreferenciasDeUsuario(dataPreferenciasDeUsuario.getId(),
                dataPreferenciasDeUsuario.getColorPrimario(),
                dataPreferenciasDeUsuario.getColorSeoundario(),
                mapper.apply(dataPreferenciasDeUsuario.getAnimacion()));
    }
}
