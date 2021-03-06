package com.tesis.transito.persistencia.utils;

import com.tesis.transito.entidades.Documento;
import com.tesis.transito.entidades.DataDocumento;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DocumentoDataDocumentoMapper implements Function<Documento, DataDocumento> {

    @Override
    public DataDocumento apply(Documento documento) {
        if (documento.getId() != null && !documento.getId().isEmpty()) {
            return new DataDocumento(
                    documento.getId(),
                    documento.getNombre(),
                    documento.getArchivo(),
                    documento.getExtension(),
                    documento.getFechaCreacion(),
                    documento.getFechaActualizacion(),
                    documento.getRutaDeDescarga());
        }
        return new DataDocumento(
                documento.getNombre(),
                documento.getArchivo(),
                documento.getFechaCreacion(),
                documento.getFechaActualizacion(),
                documento.getRutaDeDescarga(),
                documento.getExtension());
    }
}
