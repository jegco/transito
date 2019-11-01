package com.tesis.persistencia.modelos;

import java.util.List;

public class DataPaso {
    private String titulo;
    private String descripcion;
    private List<DataDocumento> anexos;

    public DataPaso(String titulo, String descripcion, List<DataDocumento> anexos) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.anexos = anexos;
    }

    public DataPaso() {

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

    public List<DataDocumento> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<DataDocumento> anexos) {
        this.anexos = anexos;
    }
}
