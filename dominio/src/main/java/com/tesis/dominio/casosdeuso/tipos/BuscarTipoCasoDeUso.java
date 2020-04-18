package com.tesis.dominio.casosdeuso.tipos;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Tipo;
import org.springframework.stereotype.Service;
import reactor.core.CorePublisher;

@Service
public class BuscarTipoCasoDeUso extends CasoDeUsoImpl<String, Tipo> {

    private final Delegado<String, Tipo> delegado;

    public BuscarTipoCasoDeUso(Delegado<String, Tipo> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected CorePublisher<Tipo> construirCasoDeUso(String s) {
        return delegado.buscar(s);
    }
}
