package com.tesis.persistencia.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DataUsuario {

    @Id
    private String id;
    private String nombre;
    private String password;
    private String correoElectronico;
    private String numeroDeTelefono;
    private String rol;
    private boolean active;

    public DataUsuario(String nombre, String password, String correoElectronico, String numeroDeTelefono, String rol) {
        this.nombre = nombre;
        this.password = password;
        this.correoElectronico = correoElectronico;
        this.numeroDeTelefono = numeroDeTelefono;
        this.rol = rol;
        this.active = false;
    }
}
