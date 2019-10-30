package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.Paso;
import com.tesis.persistencia.modelos.DataDocumento;
import com.tesis.persistencia.modelos.DataPaso;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class PasoDataPasoMapper implements Function<Paso, DataPaso> {

    private final DocumentoDataDocumentoMapper mapper;

    public PasoDataPasoMapper(DocumentoDataDocumentoMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public DataPaso apply(Paso paso) {
        List<DataDocumento> documentos = new ArrayList<>();
        paso.getAnexos().forEach(documento -> documentos.add(mapper.apply(documento)));
        return new DataPaso(paso.getTitulo(), paso.getDescripcion(), documentos);
    }
}
