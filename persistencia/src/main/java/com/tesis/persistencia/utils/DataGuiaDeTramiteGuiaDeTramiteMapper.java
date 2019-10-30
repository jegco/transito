package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.Documento;
import com.tesis.dominio.modelos.GuiaDeTramite;
import com.tesis.dominio.modelos.Paso;
import com.tesis.persistencia.modelos.DataGuiaDeTramite;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class DataGuiaDeTramiteGuiaDeTramiteMapper implements Function<DataGuiaDeTramite, GuiaDeTramite> {

    private final DataDocumentoDocumentoMapper dataDocumentoDocumentoMapper;
    private final DataPasoPasoMapper pasoDataPasoMapper;

    public DataGuiaDeTramiteGuiaDeTramiteMapper(DataDocumentoDocumentoMapper dataDocumentoDocumentoMapper,
                                                DataPasoPasoMapper pasoDataPasoMapper) {
        this.dataDocumentoDocumentoMapper = dataDocumentoDocumentoMapper;
        this.pasoDataPasoMapper = pasoDataPasoMapper;
    }

    @Override
    public GuiaDeTramite apply(DataGuiaDeTramite dataGuiaDeTramite) {
        List<Documento> documentos = new ArrayList<>();
        List<Paso> pasos = new ArrayList<>();
        dataGuiaDeTramite.getDataPasos().forEach(paso -> pasos.add(pasoDataPasoMapper.apply(paso)));
        dataGuiaDeTramite.getFormularios().forEach(dataDocumento -> documentos.add(dataDocumentoDocumentoMapper.apply(dataDocumento)));
        return new GuiaDeTramite(dataGuiaDeTramite.getId(),
                dataGuiaDeTramite.getTitulo(),
                dataGuiaDeTramite.getDescripcion(),
                documentos,
                pasos,
                dataGuiaDeTramite.getTipo());
    }
}
