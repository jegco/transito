package com.tesis.transito.dominio.casosdeuso.guiasdetramite;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.entidades.GuiaDeTramite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CasoDeUsoEliminarGuia extends CasoDeUsoImpl<GuiaDeTramite, Void> {

    private final Delegado<GuiaDeTramiteParams, GuiaDeTramite> delegado;

    @Autowired
    public CasoDeUsoEliminarGuia(Delegado<GuiaDeTramiteParams, GuiaDeTramite> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Mono<Void> construirCasoDeUso(GuiaDeTramite guiaDeTramite) {
        return delegado.eliminar(guiaDeTramite);
    }
}
