package com.tesis.transito.entidades;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Tipo {

    private String id;
    private String nombre;
    private Documento icono;

    public Tipo(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
