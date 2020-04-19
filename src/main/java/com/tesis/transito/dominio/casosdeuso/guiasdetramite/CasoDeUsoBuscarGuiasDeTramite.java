package com.tesis.transito.dominio.casosdeuso.guiasdetramite;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.dominio.modelos.GuiaDeTramite;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CasoDeUsoBuscarGuiasDeTramite extends CasoDeUsoImpl<GuiaDeTramiteParams, GuiaDeTramite> {

    private final Delegado<GuiaDeTramiteParams, GuiaDeTramite> delegado;

    public CasoDeUsoBuscarGuiasDeTramite(Delegado<GuiaDeTramiteParams, GuiaDeTramite> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Flux<GuiaDeTramite> construirCasoDeUso(GuiaDeTramiteParams params) {
        return delegado.buscar(params);
    }
}
