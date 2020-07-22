package com.tesis.transito.dominio.casosdeuso.params;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class GuiaDeTramiteParams {
    private String titulo;
    private String descripcion;
    private String tipo;

    public GuiaDeTramiteParams(String tipo) {
        this.tipo = tipo;
    }
}
