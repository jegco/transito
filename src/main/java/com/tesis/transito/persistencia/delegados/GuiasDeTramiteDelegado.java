package com.tesis.transito.persistencia.delegados;

import com.tesis.transito.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.dominio.modelos.GuiaDeTramite;
import com.tesis.transito.persistencia.repositorios.GuiasDeTramitesRepositorio;
import com.tesis.transito.persistencia.utils.DataGuiaDeTramiteGuiaDeTramiteMapper;
import com.tesis.transito.persistencia.utils.GuiaDeTramiteDataGuiaDeTramiteMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuiasDeTramiteDelegado implements Delegado<GuiaDeTramiteParams, GuiaDeTramite> {

    private final GuiasDeTramitesRepositorio repositorio;
    private final DataGuiaDeTramiteGuiaDeTramiteMapper dataGuiaDeTramiteGuiaDeTramiteMapper;
    private final GuiaDeTramiteDataGuiaDeTramiteMapper guiaDeTramiteDataGuiaDeTramiteMapper;

    public GuiasDeTramiteDelegado(GuiasDeTramitesRepositorio repositorio, DataGuiaDeTramiteGuiaDeTramiteMapper dataGuiaDeTramiteGuiaDeTramiteMapper, GuiaDeTramiteDataGuiaDeTramiteMapper guiaDeTramiteDataGuiaDeTramiteMapper) {
        this.repositorio = repositorio;
        this.dataGuiaDeTramiteGuiaDeTramiteMapper = dataGuiaDeTramiteGuiaDeTramiteMapper;
        this.guiaDeTramiteDataGuiaDeTramiteMapper = guiaDeTramiteDataGuiaDeTramiteMapper;
    }

    @Override
    public Mono<GuiaDeTramite> crear(GuiaDeTramite entidad) {
        return repositorio.save(guiaDeTramiteDataGuiaDeTramiteMapper.apply(entidad))
                .flatMap(dataGuiaDeTramiteGuiaDeTramiteMapper);
    }

    @Override
    public Flux<GuiaDeTramite> crear(List<GuiaDeTramite> entidades) {
        return repositorio.insert(entidades
                .stream()
                .map(guiaDeTramiteDataGuiaDeTramiteMapper)
                .collect(Collectors.toList())).flatMap(dataGuiaDeTramiteGuiaDeTramiteMapper);
    }

    @Override
    public Mono<GuiaDeTramite> actualizar(GuiaDeTramite entidad) {
        return repositorio.save(guiaDeTramiteDataGuiaDeTramiteMapper.apply(entidad))
                .flatMap(dataGuiaDeTramiteGuiaDeTramiteMapper);
    }

    @Override
    public Flux<GuiaDeTramite> buscar(GuiaDeTramiteParams parametroDeBusqueda) {
        if (parametroDeBusqueda != null) {
            if (parametroDeBusqueda.getDescripcion() != null && parametroDeBusqueda.getTitulo() != null) {
                return repositorio.findAllByTituloContainsOrDescripcionContainsOrTipoContains(
                        parametroDeBusqueda.getTitulo(), parametroDeBusqueda.getDescripcion(), parametroDeBusqueda.getTipo())
                        .flatMap(dataGuiaDeTramiteGuiaDeTramiteMapper);
            } else if (parametroDeBusqueda.getTitulo() != null) {
                return repositorio.findDataGuiaDeTramiteByTituloContains(
                        parametroDeBusqueda.getTitulo())
                        .flatMap(dataGuiaDeTramiteGuiaDeTramiteMapper);
            } else {
                return repositorio.findAllByTipoContains(parametroDeBusqueda.getTipo())
                        .flatMap(dataGuiaDeTramiteGuiaDeTramiteMapper);
            }
        }
        return repositorio.findAll().flatMap(dataGuiaDeTramiteGuiaDeTramiteMapper);
    }

    @Override
    public Mono<Void> eliminar(GuiaDeTramite entidad) {
        return repositorio.delete(guiaDeTramiteDataGuiaDeTramiteMapper.apply(entidad));
    }
}
