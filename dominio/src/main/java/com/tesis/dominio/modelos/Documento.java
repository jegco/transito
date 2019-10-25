package com.tesis.dominio.modelos;

import java.io.File;
import java.util.Date;

public class Documento {
    private String nombre;
    private String id;
    private File archivo;
    private Date fechaCreacion;
    private Date fechaActualizacion;

    public Documento(String nombre, String id, File archivo, Date fechaCreacion, Date fechaActualizacion) {
        this.nombre = nombre;
        this.id = id;
        this.archivo = archivo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
