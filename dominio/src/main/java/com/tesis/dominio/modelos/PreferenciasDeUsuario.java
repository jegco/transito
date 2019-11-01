package com.tesis.dominio.modelos;

public class PreferenciasDeUsuario {

    private String id;
    private String colorPrimario;
    private String colorSecundario;
    private Documento icono;
    private Menu menuDeNav;

    public PreferenciasDeUsuario(String id, String colorPrimario, String colorSecundario, Documento icono, Menu menuDeNav) {
        this.id = id;
        this.colorPrimario = colorPrimario;
        this.colorSecundario = colorSecundario;
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

    public String getColorSecundario() {
        return colorSecundario;
    }

    public void setColorSecundario(String colorSecundario) {
        this.colorSecundario = colorSecundario;
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
