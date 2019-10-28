package com.tesis.persistencia.modelos;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class DataDocumento {
    private String nombre;
    @Id
    private String id;
    private String archivo;
    private Date fechaCreacion;
    private Date fechaActualizacion;

    public DataDocumento(String nombre, String id, String archivo, Date fechaCreacion, Date fechaActualizacion) {
        this.nombre = nombre;
        this.id = id;
        this.archivo = archivo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public DataDocumento(String nombre, String archivo, Date fechaCreacion, Date fechaActualizacion) {
        this.nombre = nombre;
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

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
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
