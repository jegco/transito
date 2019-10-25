package com.tesis.dominio.modelos;

import java.util.List;

public class GuiaDeTramite {
    private String id;
    private String titulo;
    private String descripcion;
    private List<Documento> formularios;
    private List<Paso> pasos;

    public GuiaDeTramite(String id, String titulo, String descripcion, List<Documento> formularios, List<Paso> pasos) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.formularios = formularios;
        this.pasos = pasos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Documento> getFormularios() {
        return formularios;
    }

    public void setFormularios(List<Documento> formularios) {
        this.formularios = formularios;
    }

    public List<Paso> getPasos() {
        return pasos;
    }

    public void setPasos(List<Paso> pasos) {
        this.pasos = pasos;
    }
}
