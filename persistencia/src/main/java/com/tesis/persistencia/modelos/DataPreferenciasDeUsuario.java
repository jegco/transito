package com.tesis.persistencia.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DataPreferenciasDeUsuario {

    @Id
    private String id;
    private String colorPrimario;
    private String colorSeoundario;
    private DataDocumento icono;
    private DataMenu dataMenuDeNav;
    private String animacion;

    public DataPreferenciasDeUsuario(String colorPrimario, String colorSeoundario, DataDocumento icono,
                                     DataMenu dataMenuDeNav, String animacion) {
        this.colorPrimario = colorPrimario;
        this.colorSeoundario = colorSeoundario;
        this.icono = icono;
        this.dataMenuDeNav = dataMenuDeNav;
        this.animacion = animacion;
    }
}
