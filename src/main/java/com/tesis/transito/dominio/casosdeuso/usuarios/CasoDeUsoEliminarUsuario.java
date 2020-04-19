package com.tesis.transito.dominio.casosdeuso.usuarios;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.casosdeuso.params.UsuarioParams;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.dominio.modelos.Usuario;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CasoDeUsoEliminarUsuario extends CasoDeUsoImpl<Usuario, Void> {

    private final Delegado<UsuarioParams, Usuario> delegado;

    public CasoDeUsoEliminarUsuario(Delegado<UsuarioParams, Usuario> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Mono<Void> construirCasoDeUso(Usuario usuario) {
        return delegado.eliminar(usuario);
    }
}
