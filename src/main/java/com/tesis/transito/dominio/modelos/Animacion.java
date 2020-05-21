package com.tesis.transito.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Animacion {

    private String id;
    private String nombre;
    private int tiempoDeEspera;
    private int tiempoDeAnimacion;
}
