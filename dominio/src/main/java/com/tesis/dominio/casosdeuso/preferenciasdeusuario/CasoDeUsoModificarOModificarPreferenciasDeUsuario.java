package com.tesis.dominio.casosdeuso.preferenciasdeusuario;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.PreferenciasDeUsuario;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CasoDeUsoModificarOModificarPreferenciasDeUsuario
        extends CasoDeUsoImpl<PreferenciasDeUsuario, PreferenciasDeUsuario> {

    private final Delegado<String, PreferenciasDeUsuario> delegado;

    public CasoDeUsoModificarOModificarPreferenciasDeUsuario(Delegado<String, PreferenciasDeUsuario> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Flux<PreferenciasDeUsuario> construirCasoDeUso(PreferenciasDeUsuario entidad) {
        return Flux.from(delegado.actualizar(entidad));
    }
}
