package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.Tipo;
import com.tesis.persistencia.modelos.DataTipo;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TipoDataTipoMapper implements Function<Tipo, DataTipo> {

    private final DocumentoDataDocumentoMapper mapper;

    public TipoDataTipoMapper(DocumentoDataDocumentoMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public DataTipo apply(Tipo tipo) {
        return tipo.getId() == null ?
                new DataTipo(tipo.getNombre(),
                        tipo.getIcono().getId()) :
                new DataTipo(tipo.getId(),
                        tipo.getNombre(),
                tipo.getIcono().getId());
    }
}
