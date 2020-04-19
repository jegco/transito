package com.tesis.transito.dominio.casosdeuso.params;

public class GuiaDeTramiteParams {
    private String titulo;
    private String descripcion;
    private String tipo;

    public GuiaDeTramiteParams(String titulo, String descripcion, String tipo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public GuiaDeTramiteParams(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
