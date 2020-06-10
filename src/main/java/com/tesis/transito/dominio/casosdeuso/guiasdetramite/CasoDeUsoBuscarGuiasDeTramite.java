package com.tesis.transito.dominio.casosdeuso.guiasdetramite;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.dominio.modelos.GuiaDeTramite;
import com.tesis.transito.dominio.utils.exceptions.GuideNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CasoDeUsoBuscarGuiasDeTramite extends CasoDeUsoImpl<GuiaDeTramiteParams, GuiaDeTramite> {

    private final Delegado<GuiaDeTramiteParams, GuiaDeTramite> delegado;

    public CasoDeUsoBuscarGuiasDeTramite(Delegado<GuiaDeTramiteParams, GuiaDeTramite> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Flux<GuiaDeTramite> construirCasoDeUso(GuiaDeTramiteParams params) {
        return delegado.buscar(params).switchIfEmpty(Mono.error(new GuideNotFoundException("guia no encontrada")));
    }
}
