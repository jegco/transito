package com.tesis.transito.presentacion.utils.mappers;

import com.tesis.transito.entidades.Usuario;
import com.tesis.transito.presentacion.modelos.VistaUsuario;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UsuarioVistaUsuarioMapper implements Function<Usuario, VistaUsuario> {

    @Override
    public VistaUsuario apply(Usuario usuario) {
        return new VistaUsuario(
                usuario.getId(),
                usuario.getNombreDeUsuario(),
                usuario.getPassword(),
                usuario.getCorreoElectronico(),
                usuario.getNumeroDeTelefono(),
                usuario.getRole(),
                usuario.isActive());
    }
}
