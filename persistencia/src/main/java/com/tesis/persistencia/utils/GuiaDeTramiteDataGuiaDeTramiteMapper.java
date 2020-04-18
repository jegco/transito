package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.Documento;
import com.tesis.dominio.modelos.GuiaDeTramite;
import com.tesis.dominio.modelos.PuntoDeAtencion;
import com.tesis.persistencia.modelos.DataDocumento;
import com.tesis.persistencia.modelos.DataGuiaDeTramite;
import com.tesis.persistencia.modelos.DataPaso;
import com.tesis.persistencia.modelos.DataPuntoDeAtencion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GuiaDeTramiteDataGuiaDeTramiteMapper implements Function<GuiaDeTramite, DataGuiaDeTramite> {

    private final DocumentoDataDocumentoMapper documentoDataDocumentoMapper;
    private final PasoDataPasoMapper pasoDataPasoMapper;
    private final PuntoDeAtencionDataPuntoDeAtencionMapper puntoDeAtencionDataPuntoDeAtencionMapper;
    private final TipoDataTipoMapper tipoDataTipoMapper;

    public GuiaDeTramiteDataGuiaDeTramiteMapper(DocumentoDataDocumentoMapper documentoDataDocumentoMapper,
                                                PasoDataPasoMapper pasoDataPasoMapper,
                                                PuntoDeAtencionDataPuntoDeAtencionMapper puntoDeAtencionDataPuntoDeAtencionMapper,
                                                TipoDataTipoMapper tipoDataTipoMapper) {
        this.documentoDataDocumentoMapper = documentoDataDocumentoMapper;
        this.pasoDataPasoMapper = pasoDataPasoMapper;
        this.puntoDeAtencionDataPuntoDeAtencionMapper = puntoDeAtencionDataPuntoDeAtencionMapper;
        this.tipoDataTipoMapper = tipoDataTipoMapper;
    }

    @Override
    public DataGuiaDeTramite apply(GuiaDeTramite guiaDeTramite) {
        List<DataPaso> pasos = new ArrayList<>();
        guiaDeTramite.getPasos().forEach(paso -> pasos.add(pasoDataPasoMapper.apply(paso)));

        if (guiaDeTramite.getId() != null && !guiaDeTramite.getId().isEmpty()) {
            return new DataGuiaDeTramite(guiaDeTramite.getId(),
                    guiaDeTramite.getTitulo(),
                    guiaDeTramite.getDescripcion(),
                    guiaDeTramite.getFormularios().stream().map(Documento::getId).collect(Collectors.toList()),
                    guiaDeTramite.getTipo().getId(),
                    guiaDeTramite.getSoporteLegal(),
                    guiaDeTramite.getPuntosDeAtencion().stream().map(PuntoDeAtencion::getId).collect(Collectors.toList()),
                    pasos);
        }
        return new DataGuiaDeTramite(
                guiaDeTramite.getTitulo(),
                guiaDeTramite.getDescripcion(),
                guiaDeTramite.getFormularios().stream().map(Documento::getId).collect(Collectors.toList()),
                pasos,
                guiaDeTramite.getTipo().getId(),
                guiaDeTramite.getSoporteLegal(),
                guiaDeTramite.getPuntosDeAtencion()
                        .stream().map(PuntoDeAtencion::getId).collect(Collectors.toList()));
    }
}
