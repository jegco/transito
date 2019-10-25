package com.tesis.persistencia.modelos;

public class DataPreferenciasDeUsuario {

    private String colorPrimario;
    private String colorSeoundario;
    private DataDocumento icono;
    private DataMenu dataMenuDeNav;

    public DataPreferenciasDeUsuario(String colorPrimario, String colorSeoundario, DataDocumento icono, DataMenu dataMenuDeNav) {
        this.colorPrimario = colorPrimario;
        this.colorSeoundario = colorSeoundario;
        this.icono = icono;
        this.dataMenuDeNav = dataMenuDeNav;
    }

    public String getColorPrimario() {
        return colorPrimario;
    }

    public void setColorPrimario(String colorPrimario) {
        this.colorPrimario = colorPrimario;
    }

    public String getColorSeoundario() {
        return colorSeoundario;
    }

    public void setColorSeoundario(String colorSeoundario) {
        this.colorSeoundario = colorSeoundario;
    }

    public DataDocumento getIcono() {
        return icono;
    }

    public void setIcono(DataDocumento icono) {
        this.icono = icono;
    }

    public DataMenu getDataMenuDeNav() {
        return dataMenuDeNav;
    }

    public void setDataMenuDeNav(DataMenu dataMenuDeNav) {
        this.dataMenuDeNav = dataMenuDeNav;
    }
}
