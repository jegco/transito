package com.tesis.transito.dominio.casosdeuso.params;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class UsuarioParams {
    private String nombreUsuario;
    private String password;
    private String rol;
    private boolean active;

    public UsuarioParams(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    public UsuarioParams(String rol, boolean active) {
        this.active = active;
        this.rol = rol;
    }
}
