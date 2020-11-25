package com.tesis.transito.persistencia.utils;

import com.tesis.transito.entidades.Usuario;
import com.tesis.transito.entidades.DataUsuario;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UsuarioDataUsuarioMapper implements Function<Usuario, DataUsuario> {

    @Override
    public DataUsuario apply(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId().isEmpty()) {
            return new DataUsuario(usuario.getNombreDeUsuario(),
                    usuario.getPassword(),
                    usuario.getCorreoElectronico(),
                    usuario.getNumeroDeTelefono(),
                    usuario.getRole());
        }
        return new DataUsuario(usuario.getId(),
                usuario.getNombreDeUsuario(),
                usuario.getPassword(),
                usuario.getCorreoElectronico(),
                usuario.getNumeroDeTelefono(),
                usuario.getRole(),
                usuario.isActive());
    }

}
