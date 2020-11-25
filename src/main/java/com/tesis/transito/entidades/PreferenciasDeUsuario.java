package com.tesis.transito.entidades;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class PreferenciasDeUsuario {

    private String id;
    private String colorPrimario;
    private String colorSecundario;
    private Animacion animacion;

}
