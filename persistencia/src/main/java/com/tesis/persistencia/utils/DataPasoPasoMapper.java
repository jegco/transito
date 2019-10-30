package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.Documento;
import com.tesis.dominio.modelos.Paso;
import com.tesis.persistencia.modelos.DataPaso;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class DataPasoPasoMapper implements Function<DataPaso, Paso> {

    final DataDocumentoDocumentoMapper mapper;

    public DataPasoPasoMapper(DataDocumentoDocumentoMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Paso apply(DataPaso dataPaso) {
        List<Documento> documentos = new ArrayList<>();
        dataPaso.getAnexos().forEach(documento -> documentos.add(mapper.apply(documento)));
        return new Paso(dataPaso.getTitulo(), dataPaso.getDescripcion(), documentos);
    }
}
