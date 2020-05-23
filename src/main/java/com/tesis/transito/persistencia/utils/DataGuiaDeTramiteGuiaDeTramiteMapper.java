package com.tesis.transito.persistencia.utils;

import com.tesis.transito.dominio.modelos.GuiaDeTramite;
import com.tesis.transito.persistencia.modelos.DataGuiaDeTramite;
import com.tesis.transito.persistencia.repositorios.DocumentoRepositorio;
import com.tesis.transito.persistencia.repositorios.PuntoDeAtencionRepositorio;
import com.tesis.transito.persistencia.repositorios.TiposRepositorio;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DataGuiaDeTramiteGuiaDeTramiteMapper implements Function<DataGuiaDeTramite, Mono<GuiaDeTramite>> {

    private final DataDocumentoDocumentoMapper dataDocumentoDocumentoMapper;
    private final DataPasoPasoMapper dataPasoPasoMapper;
    private final DataPuntoDeAtencionPuntoDeAtencionMapper dataPuntoDeAtencionPuntoDeAtencionMapper;
    private final DataTipoTipoMapper dataTipoTipoMapper;
    private final DocumentoRepositorio documentoRepositorio;
    private final TiposRepositorio tiposRepositorio;
    private final PuntoDeAtencionRepositorio puntoDeAtencionRepositorio;

    public DataGuiaDeTramiteGuiaDeTramiteMapper(DataDocumentoDocumentoMapper dataDocumentoDocumentoMapper,
                                                DataPasoPasoMapper dataPasoPasoMapper,
                                                DataPuntoDeAtencionPuntoDeAtencionMapper dataPuntoDeAtencionPuntoDeAtencionMapper, DataTipoTipoMapper dataTipoTipoMapper, DocumentoRepositorio documentoRepositorio, TiposRepositorio tiposRepositorio, PuntoDeAtencionRepositorio puntoDeAtencionRepositorio) {
        this.dataDocumentoDocumentoMapper = dataDocumentoDocumentoMapper;
        this.dataPasoPasoMapper = dataPasoPasoMapper;
        this.dataPuntoDeAtencionPuntoDeAtencionMapper = dataPuntoDeAtencionPuntoDeAtencionMapper;
        this.dataTipoTipoMapper = dataTipoTipoMapper;
        this.documentoRepositorio = documentoRepositorio;
        this.tiposRepositorio = tiposRepositorio;
        this.puntoDeAtencionRepositorio = puntoDeAtencionRepositorio;
    }

    @Override
    public Mono<GuiaDeTramite> apply(DataGuiaDeTramite dataGuiaDeTramite) {

        Mono<GuiaDeTramite> guiaDeTramite = dataPasoPasoMapper.apply(dataGuiaDeTramite.getDataPasos())
                .map(pasos -> new GuiaDeTramite(dataGuiaDeTramite.getId(),
                        dataGuiaDeTramite.getTitulo(),
                        dataGuiaDeTramite.getDescripcion(),
                        pasos,
                        dataGuiaDeTramite.getSoporteLegal()))
                .flatMap(guia -> tiposRepositorio.findById(dataGuiaDeTramite.getTipo())
                        .flatMap(dataTipoTipoMapper)
                        .map(tipo -> {
                            guia.setTipo(tipo);
                            return guia;
                        })).zipWith(puntoDeAtencionRepositorio.findAllById(dataGuiaDeTramite.getPuntosDeAtencion()).collectList(),
                        (g, pts) -> {
                            g.setPuntosDeAtencion(
                                    pts.stream().map(dataPuntoDeAtencionPuntoDeAtencionMapper).collect(Collectors.toList())
                            );
                            return g;
                        });
        if (dataGuiaDeTramite.getAnexo() != null) {
            guiaDeTramite = guiaDeTramite.zipWith(documentoRepositorio.findById(dataGuiaDeTramite.getAnexo() != null ? dataGuiaDeTramite.getAnexo() : ""),
                    (g, docs) -> {
                        g.setAnexo(dataDocumentoDocumentoMapper.apply(docs));
                        return g;
                    });
        }

        return guiaDeTramite;
    }
}
