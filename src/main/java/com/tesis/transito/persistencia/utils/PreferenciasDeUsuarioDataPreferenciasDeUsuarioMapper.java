package com.tesis.transito.persistencia.utils;

import com.tesis.transito.dominio.modelos.PreferenciasDeUsuario;
import com.tesis.transito.persistencia.modelos.DataPreferenciasDeUsuario;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PreferenciasDeUsuarioDataPreferenciasDeUsuarioMapper implements
        Function<PreferenciasDeUsuario, DataPreferenciasDeUsuario> {

    private final MenuDataMenuMapper mapper;
    private final DocumentoDataDocumentoMapper documentoMapper;

    public PreferenciasDeUsuarioDataPreferenciasDeUsuarioMapper(MenuDataMenuMapper mapper,
                                                                DocumentoDataDocumentoMapper documentoMapper) {
        this.mapper = mapper;
        this.documentoMapper = documentoMapper;
    }

    @Override
    public DataPreferenciasDeUsuario apply(PreferenciasDeUsuario preferenciasDeUsuario) {
        if (preferenciasDeUsuario.getId() != null && !preferenciasDeUsuario.getId().isEmpty()) {
            return new DataPreferenciasDeUsuario(preferenciasDeUsuario.getId(),
                    preferenciasDeUsuario.getColorPrimario(), preferenciasDeUsuario.getColorSecundario(),
                    documentoMapper.apply(preferenciasDeUsuario.getIcono()),
                    mapper.apply(preferenciasDeUsuario.getMenuDeNav()),
                    preferenciasDeUsuario.getAnimacion());
        }
        return new DataPreferenciasDeUsuario(
                preferenciasDeUsuario.getColorPrimario(), preferenciasDeUsuario.getColorSecundario(),
                documentoMapper.apply(preferenciasDeUsuario.getIcono()),
                mapper.apply(preferenciasDeUsuario.getMenuDeNav()),
                preferenciasDeUsuario.getAnimacion());
    }
}
