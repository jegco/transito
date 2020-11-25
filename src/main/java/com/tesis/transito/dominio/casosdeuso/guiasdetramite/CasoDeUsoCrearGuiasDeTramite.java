package com.tesis.transito.dominio.casosdeuso.guiasdetramite;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.entidades.GuiaDeTramite;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CasoDeUsoCrearGuiasDeTramite extends CasoDeUsoImpl<GuiaDeTramite, GuiaDeTramite> {

    private final Delegado<GuiaDeTramiteParams, GuiaDeTramite> delegado;

    public CasoDeUsoCrearGuiasDeTramite(Delegado<GuiaDeTramiteParams, GuiaDeTramite> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Mono<GuiaDeTramite> construirCasoDeUso(GuiaDeTramite guiaDeTramite) {
        return delegado.crear(guiaDeTramite);
    }
}
