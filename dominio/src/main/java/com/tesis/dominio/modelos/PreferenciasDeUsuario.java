package com.tesis.dominio.modelos;

public class PreferenciasDeUsuario {

    private String id;
    private String colorPrimario;
    private String colorSeoundario;
    private Documento icono;
    private Menu menuDeNav;

    public PreferenciasDeUsuario(String id, String colorPrimario, String colorSeoundario, Documento icono, Menu menuDeNav) {
        this.id = id;
        this.colorPrimario = colorPrimario;
        this.colorSeoundario = colorSeoundario;
        this.icono = icono;
        this.menuDeNav = menuDeNav;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
