package com.tesis.transito.persistencia.utils;

import com.tesis.transito.dominio.modelos.PreferenciasDeUsuario;
import com.tesis.transito.persistencia.modelos.DataPreferenciasDeUsuario;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DataPreferenciasDeUsuarioPreferenciasDeUsuarioMapper
        implements Function<DataPreferenciasDeUsuario, PreferenciasDeUsuario> {

    private final DataMenuMenuMapper mapper;
    private final DataDocumentoDocumentoMapper documentoMapper;

    public DataPreferenciasDeUsuarioPreferenciasDeUsuarioMapper(DataMenuMenuMapper mapper, DataDocumentoDocumentoMapper documentoMapper) {
        this.mapper = mapper;
        this.documentoMapper = documentoMapper;
    }

    @Override
    public PreferenciasDeUsuario apply(DataPreferenciasDeUsuario dataPreferenciasDeUsuario) {
        return new PreferenciasDeUsuario(dataPreferenciasDeUsuario.getId(),
                dataPreferenciasDeUsuario.getColorPrimario(),
                dataPreferenciasDeUsuario.getColorSeoundario(),
                dataPreferenciasDeUsuario.getAnimacion(),
                documentoMapper.apply(dataPreferenciasDeUsuario.getIcono()),
                mapper.apply(dataPreferenciasDeUsuario.getDataMenuDeNav()));
    }
}
