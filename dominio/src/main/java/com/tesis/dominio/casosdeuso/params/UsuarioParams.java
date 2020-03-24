package com.tesis.dominio.casosdeuso.params;

public class UsuarioParams {
    private String nombreUsuario;
    private String password;
    private String rol;
    private boolean active;

    public UsuarioParams() {

    }

    public UsuarioParams(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    public UsuarioParams(String nombreUsuario, String password, String rol, boolean active) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
        this.active = active;
    }

    public UsuarioParams(String rol, boolean active) {
        this.active = active;
        this.rol = rol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    public boolean isActive() {
        return active;
    }
}
