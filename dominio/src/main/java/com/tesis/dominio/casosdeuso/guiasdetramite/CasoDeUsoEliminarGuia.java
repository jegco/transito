package com.tesis.dominio.casosdeuso.guiasdetramite;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.GuiaDeTramite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CasoDeUsoEliminarGuia extends CasoDeUsoImpl<GuiaDeTramite, Void> {

    private final Delegado<GuiaDeTramiteParams, GuiaDeTramite> delegado;

    @Autowired
    public CasoDeUsoEliminarGuia(Delegado<GuiaDeTramiteParams, GuiaDeTramite> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Flux<Void> construirCasoDeUso(GuiaDeTramite guiaDeTramite) {
        return Flux.from(delegado.eliminar(guiaDeTramite));
    }
}
