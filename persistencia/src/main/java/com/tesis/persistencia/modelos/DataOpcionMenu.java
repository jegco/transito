package com.tesis.persistencia.modelos;

import org.springframework.data.annotation.Id;

import java.util.List;

public class DataOpcionMenu {

    @Id
    private String id;
    private List<String> opciones;
    private String titulo;

    public DataOpcionMenu(List<String> opciones, String titulo) {
        this.opciones = opciones;
        this.titulo = titulo;
    }

    public DataOpcionMenu(String id, List<String> opciones, String titulo) {
        this.id = id;
        this.opciones = opciones;
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
