package com.tesis.transito.entidades;

import lombok.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
public class PuntoDeAtencion {

    private String id;
    private String nombre;
    private String direccion;
    private String latitud;
    private String longitud;

}
