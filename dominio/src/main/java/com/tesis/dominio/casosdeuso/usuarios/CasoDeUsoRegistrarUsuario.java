package com.tesis.dominio.casosdeuso.usuarios;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.casosdeuso.params.LoginParam;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Usuario;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CasoDeUsoRegistrarUsuario extends CasoDeUsoImpl<Usuario, Usuario> {

    private final Delegado<LoginParam, Usuario> usuarioDelegado;

    public CasoDeUsoRegistrarUsuario(Delegado<LoginParam, Usuario> usuarioDelegado) {
        this.usuarioDelegado = usuarioDelegado;
    }

    @Override
    protected Flux<Usuario> construirCasoDeUso(Usuario usuario) {
        return Flux.from(usuarioDelegado.crear(usuario));
    }
}
