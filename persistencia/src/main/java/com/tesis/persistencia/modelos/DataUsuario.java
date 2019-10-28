package com.tesis.persistencia.modelos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class DataUsuario {

    @Id
    private String id;
    private String nombre;
    private String password;
    private String correoElectronico;
    private String numeroDeTelefono;

    public DataUsuario() {
    }

    public DataUsuario(String id, String nombre, String password, String correoElectronico, String numeroDeTelefono) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.correoElectronico = correoElectronico;
        this.numeroDeTelefono = numeroDeTelefono;
    }

    public DataUsuario(String nombre, String password, String correoElectronico, String numeroDeTelefono) {
        this.nombre = nombre;
        this.password = password;
        this.correoElectronico = correoElectronico;
        this.numeroDeTelefono = numeroDeTelefono;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNumeroDeTelefono() {
        return numeroDeTelefono;
    }

    public void setNumeroDeTelefono(String numeroDeTelefono) {
        this.numeroDeTelefono = numeroDeTelefono;
    }
}
