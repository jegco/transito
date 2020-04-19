package com.tesis.transito.persistencia.utils;

import com.tesis.transito.dominio.modelos.Usuario;
import com.tesis.transito.persistencia.modelos.DataUsuario;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DataUsuarioUsuarioMapper implements Function<DataUsuario, Usuario> {

    @Override
    public Usuario apply(DataUsuario dataUsuario) {
        return new Usuario(dataUsuario.getId(),
                dataUsuario.getNombre(),
                dataUsuario.getPassword(),
                dataUsuario.getCorreoElectronico(),
                dataUsuario.getNumeroDeTelefono(),
                dataUsuario.getRol(),
                dataUsuario.isActive());
    }
}
