package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.Documento;
import com.tesis.persistencia.modelos.DataDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DataDocumentoDocumentoMapper implements Function<DataDocumento, Documento> {

    @Autowired private StorageService storageService;

    @Override
    public Documento apply(DataDocumento dataDocumento) {
        return new Documento(dataDocumento.getNombre(),
                dataDocumento.getId(),
                storageService.cargarDocumento(dataDocumento.getNombre()),
                dataDocumento.getFechaCreacion(),
                dataDocumento.getFechaActualizacion());
    }
}
