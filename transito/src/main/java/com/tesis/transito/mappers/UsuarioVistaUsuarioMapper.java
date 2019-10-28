package com.tesis.transito.mappers;

import com.tesis.dominio.modelos.Usuario;
import com.tesis.transito.modelos.VistaUsuario;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UsuarioVistaUsuarioMapper implements Function<Usuario, VistaUsuario> {

    @Override
    public VistaUsuario apply(Usuario usuario) {
        return new VistaUsuario(
                usuario.getId(),
                usuario.getNombreDeUsuario(),
                usuario.getContraseña(),
                usuario.getCorreoElectronico(),
                usuario.getNumeroDeTelefono());
    }
}
