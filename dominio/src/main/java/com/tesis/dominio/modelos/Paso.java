package com.tesis.dominio.modelos;

import java.util.List;

public class Paso {
    private String titulo;
    private String descripcion;
    private List<Documento> anexos;

    public Paso(String titulo, String descripcion, List<Documento> anexos) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.anexos = anexos;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Documento> getAnexos() {
        return anexos;
    }
}
