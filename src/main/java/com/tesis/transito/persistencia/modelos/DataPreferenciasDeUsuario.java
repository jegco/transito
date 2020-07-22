package com.tesis.transito.persistencia.modelos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class DataPreferenciasDeUsuario {

    @Id
    private String id;
    private String colorPrimario;
    private String colorSeoundario;
    private DataAnimacion animacion;

    public DataPreferenciasDeUsuario(String colorPrimario, String colorSeoundario, DataAnimacion animacion) {
        this.colorPrimario = colorPrimario;
        this.colorSeoundario = colorSeoundario;
        this.animacion = animacion;
    }
}
