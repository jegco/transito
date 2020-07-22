package com.tesis.transito.dominio.modelos;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Usuario {
    private String id;
    private String nombreDeUsuario;
    private String contraseña;
    private String correoElectronico;
    private String numeroDeTelefono;
    private String role;
    private boolean active;

}
