package com.tesis.persistencia.modelos;

import org.springframework.data.annotation.Id;

import java.util.List;

public class DataGuiaDeTramite {

    @Id
    private String id;
    private String titulo;
    private String descripcion;
    private List<DataDocumento> formularios;
    private List<DataPaso> dataPasos;
    private String tipo;

    public DataGuiaDeTramite(String id, String titulo, String descripcion, List<DataDocumento> formularios, List<DataPaso> dataPasos, String tipo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.formularios = formularios;
        this.dataPasos = dataPasos;
        this.tipo = tipo;
    }

    public DataGuiaDeTramite(String titulo, String descripcion, List<DataDocumento> formularios, List<DataPaso> dataPasos, String tipo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.formularios = formularios;
        this.dataPasos = dataPasos;
        this.tipo = tipo;
    }

    public DataGuiaDeTramite() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
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
