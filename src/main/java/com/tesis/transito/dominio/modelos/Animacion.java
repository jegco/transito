package com.tesis.transito.dominio.modelos;

import lombok.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class Animacion {

    private String id;
    private String nombre;
    private int tiempoDeEspera;
    private int tiempoDeAnimacion;
}
