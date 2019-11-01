package com.tesis.dominio.casosdeuso.guiasdetramite;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.GuiaDeTramite;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CasoDeUsoCrearGuiasDeTramite extends CasoDeUsoImpl<GuiaDeTramite, GuiaDeTramite> {

    private final Delegado<GuiaDeTramiteParams, GuiaDeTramite> delegado;

    public CasoDeUsoCrearGuiasDeTramite(Delegado<GuiaDeTramiteParams, GuiaDeTramite> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Flux<GuiaDeTramite> construirCasoDeUso(GuiaDeTramite guiaDeTramite) {
        return Flux.from(delegado.crear(guiaDeTramite));
    }
}
