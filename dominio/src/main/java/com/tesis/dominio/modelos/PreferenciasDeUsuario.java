package com.tesis.dominio.modelos;

public class PreferenciasDeUsuario {

    private String colorPrimario;
    private String colorSeoundario;
    private Documento icono;
    private Menu menuDeNav;

    public PreferenciasDeUsuario(String colorPrimario, String colorSeoundario, Documento icono, Menu menuDeNav) {
        this.colorPrimario = colorPrimario;
        this.colorSeoundario = colorSeoundario;
        this.icono = icono;
        this.menuDeNav = menuDeNav;
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

    public Documento getIcono() {
        return icono;
    }

    public void setIcono(Documento icono) {
        this.icono = icono;
    }

    public Menu getMenuDeNav() {
        return menuDeNav;
    }

    public void setMenuDeNav(Menu menuDeNav) {
        this.menuDeNav = menuDeNav;
    }
}
