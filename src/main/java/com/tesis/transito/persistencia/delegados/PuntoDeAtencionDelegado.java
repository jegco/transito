package com.tesis.transito.persistencia.delegados;

import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.dominio.modelos.PuntoDeAtencion;
import com.tesis.transito.persistencia.modelos.DataPuntoDeAtencion;
import com.tesis.transito.persistencia.repositorios.PuntoDeAtencionRepositorio;
import com.tesis.transito.persistencia.utils.DataPuntoDeAtencionPuntoDeAtencionMapper;
import com.tesis.transito.persistencia.utils.PuntoDeAtencionDataPuntoDeAtencionMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PuntoDeAtencionDelegado implements Delegado<String, PuntoDeAtencion> {

    private final PuntoDeAtencionRepositorio repositorio;
    private final PuntoDeAtencionDataPuntoDeAtencionMapper puntoDeAtencionDataPuntoDeAtencionMapper;
    private final DataPuntoDeAtencionPuntoDeAtencionMapper dataPuntoDeAtencionPuntoDeAtencionMapper;

    public PuntoDeAtencionDelegado(PuntoDeAtencionRepositorio repositorio,
                                   PuntoDeAtencionDataPuntoDeAtencionMapper puntoDeAtencionDataPuntoDeAtencionMapper,
                                   DataPuntoDeAtencionPuntoDeAtencionMapper dataPuntoDeAtencionPuntoDeAtencionMapper) {
        this.repositorio = repositorio;
        this.puntoDeAtencionDataPuntoDeAtencionMapper = puntoDeAtencionDataPuntoDeAtencionMapper;
        this.dataPuntoDeAtencionPuntoDeAtencionMapper = dataPuntoDeAtencionPuntoDeAtencionMapper;
    }

    @Override
    public Mono<PuntoDeAtencion> crear(PuntoDeAtencion entidad) {
        return repositorio.save(puntoDeAtencionDataPuntoDeAtencionMapper.apply(entidad))
                .map(dataPuntoDeAtencionPuntoDeAtencionMapper);
    }

    @Override
    public Flux<PuntoDeAtencion> crear(List<PuntoDeAtencion> entidades) {
        List<DataPuntoDeAtencion> entidadesAguardar = entidades.stream()
                .map(puntoDeAtencionDataPuntoDeAtencionMapper).collect(Collectors.toList());
        return repositorio.insert(entidadesAguardar).map(dataPuntoDeAtencionPuntoDeAtencionMapper);
    }

    @Override
    public Mono<PuntoDeAtencion> actualizar(PuntoDeAtencion entidad) {
        return repositorio.save(puntoDeAtencionDataPuntoDeAtencionMapper.apply(entidad))
                .map(dataPuntoDeAtencionPuntoDeAtencionMapper);
    }

    @Override
    public Flux<PuntoDeAtencion> buscar(String s) {
        return s != null ? repositorio.findDataPuntoDeAtencionByNombre(s).map(dataPuntoDeAtencionPuntoDeAtencionMapper)
                : repositorio.findAll().map(dataPuntoDeAtencionPuntoDeAtencionMapper);
    }

    @Override
    public Mono<Void> eliminar(PuntoDeAtencion entidad) {
        return repositorio.delete(puntoDeAtencionDataPuntoDeAtencionMapper.apply(entidad));
    }
}
