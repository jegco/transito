package com.tesis.persistencia.delegados;

import com.tesis.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.GuiaDeTramite;
import com.tesis.persistencia.repositorios.GuiasDeTramitesRepositorio;
import com.tesis.persistencia.utils.DataGuiaDeTramiteGuiaDeTramiteMapper;
import com.tesis.persistencia.utils.GuiaDeTramiteDataGuiaDeTramiteMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
                .map(dataGuiaDeTramiteGuiaDeTramiteMapper);
    }

    @Override
    public Mono<GuiaDeTramite> actualizar(GuiaDeTramite entidad) {
        return repositorio.save(guiaDeTramiteDataGuiaDeTramiteMapper.apply(entidad))
                .map(dataGuiaDeTramiteGuiaDeTramiteMapper);
    }

    @Override
    public Flux<GuiaDeTramite> buscar(GuiaDeTramiteParams parametroDeBusqueda) {
        if (parametroDeBusqueda != null) {
            if (parametroDeBusqueda.getDescripcion() != null && parametroDeBusqueda.getTitulo() != null) {
                return repositorio.findAllByTituloContainsOrDescripcionContainsOrTipoContains(
                        parametroDeBusqueda.getTitulo(), parametroDeBusqueda.getDescripcion(), parametroDeBusqueda.getTipo())
                        .map(dataGuiaDeTramiteGuiaDeTramiteMapper);
            } else {
                return repositorio.findAllByTipoContains(parametroDeBusqueda.getTipo())
                        .map(dataGuiaDeTramiteGuiaDeTramiteMapper);
            }
        }
        return repositorio.findAll().map(dataGuiaDeTramiteGuiaDeTramiteMapper);
    }

    @Override
    public Mono<Void> eliminar(GuiaDeTramite entidad) {
        return repositorio.delete(guiaDeTramiteDataGuiaDeTramiteMapper.apply(entidad));
    }
}
