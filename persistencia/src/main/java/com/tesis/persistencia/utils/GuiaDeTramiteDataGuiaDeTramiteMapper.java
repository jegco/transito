package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.GuiaDeTramite;
import com.tesis.persistencia.modelos.DataDocumento;
import com.tesis.persistencia.modelos.DataGuiaDeTramite;
import com.tesis.persistencia.modelos.DataPaso;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class GuiaDeTramiteDataGuiaDeTramiteMapper implements Function<GuiaDeTramite, DataGuiaDeTramite> {

    final
    DocumentoDataDocumentoMapper documentoDataDocumentoMapper;
    final
    PasoDataPasoMapper pasoDataPasoMapper;

    public GuiaDeTramiteDataGuiaDeTramiteMapper(DocumentoDataDocumentoMapper documentoDataDocumentoMapper,
                                                PasoDataPasoMapper pasoDataPasoMapper) {
        this.documentoDataDocumentoMapper = documentoDataDocumentoMapper;
        this.pasoDataPasoMapper = pasoDataPasoMapper;
    }

    @Override
    public DataGuiaDeTramite apply(GuiaDeTramite guiaDeTramite) {
        List<DataDocumento> documentos = new ArrayList<>();
        List<DataPaso> pasos = new ArrayList<>();
        guiaDeTramite.getFormularios().forEach(documento -> documentos.add(documentoDataDocumentoMapper.apply(documento)));
        guiaDeTramite.getPasos().forEach(paso -> pasos.add(pasoDataPasoMapper.apply(paso)));

        if (guiaDeTramite.getId() != null && !guiaDeTramite.getId().isEmpty()) {
            return new DataGuiaDeTramite(guiaDeTramite.getId(),
                    guiaDeTramite.getTitulo(),
                    guiaDeTramite.getDescripcion(),
                    documentos,
                    pasos,
                    guiaDeTramite.getTipo());
        }
        return new DataGuiaDeTramite(
                guiaDeTramite.getTitulo(),
                guiaDeTramite.getDescripcion(),
                documentos,
                pasos,
                guiaDeTramite.getTipo());
    }
}
