package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.Documento;
import com.tesis.persistencia.modelos.DataDocumento;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DataDocumentoDocumentoMapper implements Function<DataDocumento, Documento> {

    @Override
    public Documento apply(DataDocumento dataDocumento) {
        return new Documento(dataDocumento.getId(),
                dataDocumento.getNombre(),
                dataDocumento.getNombre(),
                dataDocumento.getFechaCreacion(),
                dataDocumento.getFechaActualizacion(),
                dataDocumento.getRutaDeDescarga());
    }
}
