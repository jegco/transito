package com.tesis.transito.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PreferenciasDeUsuario {

    private String id;
    private String colorPrimario;
    private String colorSecundario;
    private Animacion animacion;

}
