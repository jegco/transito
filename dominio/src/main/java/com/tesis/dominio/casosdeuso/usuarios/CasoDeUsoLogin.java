package com.tesis.dominio.casosdeuso.usuarios;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.casosdeuso.params.UsuarioParams;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Usuario;
import com.tesis.dominio.utils.exceptions.UnAuthorizedException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CasoDeUsoLogin extends CasoDeUsoImpl<UsuarioParams, Usuario> {

    private final Delegado<UsuarioParams, Usuario> delegado;

    public CasoDeUsoLogin(Delegado<UsuarioParams, Usuario> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Flux<Usuario> construirCasoDeUso(UsuarioParams param) {
        return delegado.buscar(param)
                .switchIfEmpty(Mono.error(new UnAuthorizedException("Usuario no registrado")));
    }
}
