package com.tesis.dominio.modelos;

import java.util.List;

public class Menu {

    private List<OpcionMenu> opciones;

    public Menu(List<OpcionMenu> opciones) {
        this.opciones = opciones;
    }

    public List<OpcionMenu> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<OpcionMenu> opciones) {
        this.opciones = opciones;
    }
}
