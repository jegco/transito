package com.tesis.dominio.modelos;

public class Notificacion {
    private Usuario involucrado;
    private Usuario para;
    private String asunto;
    private String mensaje;

    public Notificacion() {
    }

    public Notificacion(Usuario para, Usuario involucrado, String asunto, String mensaje) {
        this.para = para;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.involucrado = involucrado;
    }

    public Notificacion(Usuario para, String asunto, String mensaje) {
        this.para = para;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Usuario getPara() {
        return para;
    }

    public Usuario getInvolucrado() {
        return involucrado;
    }
}
