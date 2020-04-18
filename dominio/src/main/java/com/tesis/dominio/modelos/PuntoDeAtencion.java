package com.tesis.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class PuntoDeAtencion {

    private String id;
    private String nombre;
    private String latitud;
    private String longitud;

}
