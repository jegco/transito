package com.tesis.persistencia.modelos;

import org.springframework.data.annotation.Id;

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

    public DataPreferenciasDeUsuario(String id, String colorPrimario, String colorSeoundario, DataDocumento icono,
                                     DataMenu dataMenuDeNav, String animacion) {
        this.id = id;
        this.colorPrimario = colorPrimario;
        this.colorSeoundario = colorSeoundario;
        this.icono = icono;
        this.dataMenuDeNav = dataMenuDeNav;
        this.animacion = animacion;
    }

    public DataPreferenciasDeUsuario() {

    }

    public String getId() {
        return id;
    }

    public String getColorPrimario() {
        return colorPrimario;
    }

    public String getColorSeoundario() {
        return colorSeoundario;
    }

    public DataDocumento getIcono() {
        return icono;
    }

    public DataMenu getDataMenuDeNav() {
        return dataMenuDeNav;
    }

    public String getAnimacion() {
        return animacion;
    }
}
