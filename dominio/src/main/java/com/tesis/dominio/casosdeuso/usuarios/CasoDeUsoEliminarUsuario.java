package com.tesis.dominio.casosdeuso.usuarios;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.casosdeuso.params.UsuarioParams;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Usuario;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CasoDeUsoEliminarUsuario extends CasoDeUsoImpl<Usuario, Void> {

    private final Delegado<UsuarioParams, Usuario> delegado;

    public CasoDeUsoEliminarUsuario(Delegado<UsuarioParams, Usuario> delegado) {
        this.delegado = delegado;
    }

    @Override
    protected Flux<Void> construirCasoDeUso(Usuario usuario) {
        return Flux.from(delegado.eliminar(usuario));
    }
}
