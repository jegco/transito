package com.tesis.transito.presentacion.utils.mappers;

import com.tesis.transito.dominio.modelos.Usuario;
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
                usuario.getContraseña(),
                usuario.getCorreoElectronico(),
                usuario.getNumeroDeTelefono(),
                usuario.getRol(),
                usuario.isActive());
    }
}
