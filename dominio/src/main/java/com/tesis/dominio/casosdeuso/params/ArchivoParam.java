package com.tesis.dominio.casosdeuso.params;

import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

public class ArchivoParam {
    protected String nombre;
    protected Flux<DataBuffer> archivo;

    public ArchivoParam(String nombre, Flux<DataBuffer> archivo) {
        this.nombre = nombre;
        this.archivo = archivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Flux<DataBuffer> getArchivo() {
        return archivo;
    }

    public void setArchivo(Flux<DataBuffer> archivo) {
        this.archivo = archivo;
    }
}
