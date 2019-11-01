package com.tesis.persistencia.modelos;

import org.springframework.data.annotation.Id;

import java.util.List;

public class DataMenu {

    @Id
    private String id;
    private List<DataOpcionMenu> opciones;

    public DataMenu(List<DataOpcionMenu> opciones) {
        this.opciones = opciones;
    }

    public DataMenu(String id, List<DataOpcionMenu> opciones) {
        this.id = id;
        this.opciones = opciones;
    }

    public DataMenu() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DataOpcionMenu> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<DataOpcionMenu> opciones) {
        this.opciones = opciones;
    }
}
