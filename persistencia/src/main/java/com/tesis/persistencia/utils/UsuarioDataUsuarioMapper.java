package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.Usuario;
import com.tesis.persistencia.modelos.DataUsuario;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UsuarioDataUsuarioMapper implements Function<Usuario, DataUsuario> {

    @Override
    public DataUsuario apply(Usuario usuario) {
        return new DataUsuario(usuario.getId(),
                usuario.getNombreDeUsuario(),
                usuario.getContraseña(),
                usuario.getCorreoElectronico(),
                usuario.getNumeroDeTelefono());
    }

}
