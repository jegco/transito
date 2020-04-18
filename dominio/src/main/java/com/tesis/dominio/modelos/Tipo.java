package com.tesis.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class Tipo {

    private String id;
    private String nombre;
    private Documento icono;

    public Tipo(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
