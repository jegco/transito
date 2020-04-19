package com.tesis.transito.dominio.casosdeuso.preferenciasdeusuario;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.dominio.modelos.PreferenciasDeUsuario;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CasoDeUsoModificarOModificarPreferenciasDeUsuario
        extends CasoDeUsoImpl<PreferenciasDeUsuario, PreferenciasDeUsuario> {

    private final Delegado<String, PreferenciasDeUsuario> delegado;

    public CasoDeUsoModificarOModificarPreferenciasDeUsuario(Delegado<String, PreferenciasDeUsuario> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Mono<PreferenciasDeUsuario> construirCasoDeUso(PreferenciasDeUsuario entidad) {
        return delegado.actualizar(entidad);
    }
}
