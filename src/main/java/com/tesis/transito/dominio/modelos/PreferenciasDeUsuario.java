package com.tesis.transito.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PreferenciasDeUsuario {

    private String id;
    private String colorPrimario;
    private String colorSecundario;
    private String animacion;
    private Documento icono;
    private Menu menuDeNav;

}
