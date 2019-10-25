package com.tesis.persistencia.modelos;

import java.util.List;

public class DataGuiaDeTramite {
    private String id;
    private String titulo;
    private String descripcion;
    private List<DataDocumento> formularios;
    private List<DataPaso> dataPasos;

    public DataGuiaDeTramite(String id, String titulo, String descripcion, List<DataDocumento> formularios, List<DataPaso> dataPasos) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.formularios = formularios;
        this.dataPasos = dataPasos;
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

    public List<DataDocumento> getFormularios() {
        return formularios;
    }

    public void setFormularios(List<DataDocumento> formularios) {
        this.formularios = formularios;
    }

    public List<DataPaso> getDataPasos() {
        return dataPasos;
    }

    public void setDataPasos(List<DataPaso> dataPasos) {
        this.dataPasos = dataPasos;
    }
}
