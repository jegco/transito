package com.tesis.transito.mappers;

import com.tesis.dominio.modelos.Usuario;
import com.tesis.transito.modelos.VistaUsuario;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class VistaUsuarioUsuarioMapper implements Function<VistaUsuario, Usuario> {

    @Override
    public Usuario apply(VistaUsuario usuario) {
        return new Usuario(usuario.getId(),
                usuario.getNombreDeUsuario(),
                usuario.getPassword(),
                usuario.getCorreoElectronico(),
                usuario.getNumeroDeTelefono(),
                usuario.getRole(),
                usuario.isActive());
    }
}
