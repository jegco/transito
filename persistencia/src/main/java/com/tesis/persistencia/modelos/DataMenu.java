package com.tesis.persistencia.modelos;

import java.util.List;

public class DataMenu {

    private List<DataOpcionMenu> opciones;

    public DataMenu(List<DataOpcionMenu> opciones) {
        this.opciones = opciones;
    }

    public List<DataOpcionMenu> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<DataOpcionMenu> opciones) {
        this.opciones = opciones;
    }
}
