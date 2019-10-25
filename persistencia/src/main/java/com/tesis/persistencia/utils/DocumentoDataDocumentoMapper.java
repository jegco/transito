package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.Documento;
import com.tesis.persistencia.modelos.DataDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DocumentoDataDocumentoMapper implements Function<Documento, DataDocumento> {

    @Autowired private StorageService storageService;

    @Override
    public DataDocumento apply(Documento documento) {
        return new DataDocumento(documento.getNombre(),
                documento.getId(),
                storageService.guardarDocumentos(documento.getArchivo()),
                documento.getFechaCreacion(),
                documento.getFechaActualizacion());
    }
}
