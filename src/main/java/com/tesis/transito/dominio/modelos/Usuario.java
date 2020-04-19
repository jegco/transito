package com.tesis.transito.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    private String id;
    private String nombreDeUsuario;
    private String contraseña;
    private String correoElectronico;
    private String numeroDeTelefono;
    private String rol;
    private boolean active;

}
