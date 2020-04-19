package com.tesis.transito.persistencia.utils;

import com.tesis.transito.dominio.modelos.Usuario;
import com.tesis.transito.persistencia.modelos.DataUsuario;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UsuarioDataUsuarioMapper implements Function<Usuario, DataUsuario> {

    @Override
    public DataUsuario apply(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId().isEmpty()) {
            return new DataUsuario(usuario.getNombreDeUsuario(),
                    usuario.getContraseña(),
                    usuario.getCorreoElectronico(),
                    usuario.getNumeroDeTelefono(),
                    usuario.getRol());
        }
        return new DataUsuario(usuario.getId(),
                usuario.getNombreDeUsuario(),
                usuario.getContraseña(),
                usuario.getCorreoElectronico(),
                usuario.getNumeroDeTelefono(),
                usuario.getRol(),
                usuario.isActive());
    }

}