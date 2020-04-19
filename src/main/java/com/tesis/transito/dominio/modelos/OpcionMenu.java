package com.tesis.transito.dominio.modelos;

import java.util.List;

public class OpcionMenu {

    private List<String> opciones;
    private String titulo;

    public OpcionMenu(List<String> opciones, String titulo) {
        this.opciones = opciones;
        this.titulo = titulo;
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
