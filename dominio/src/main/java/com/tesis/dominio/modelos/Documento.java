package com.tesis.dominio.modelos;

import java.util.Date;

public class Documento {
    private String nombre;
    private String id;
    private String archivo;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private String rutaDeDescarga;

    public Documento(String id, String nombre, String archivo, Date fechaCreacion, Date fechaActualizacion, String rutaDeDescarga) {
        this.nombre = nombre;
        this.id = id;
        this.archivo = archivo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.rutaDeDescarga = rutaDeDescarga;
    }

    public Documento() {

    }

    public String getRutaDeDescarga() {
        return rutaDeDescarga;
    }

    public void setRutaDeDescarga(String rutaDeDescarga) {
        this.rutaDeDescarga = rutaDeDescarga;
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
