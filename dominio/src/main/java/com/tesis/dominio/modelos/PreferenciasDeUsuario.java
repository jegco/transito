package com.tesis.dominio.modelos;

public class PreferenciasDeUsuario {

    private String id;
    private String colorPrimario;
    private String colorSecundario;
    private String animacion;
    private Documento icono;
    private Menu menuDeNav;

    public PreferenciasDeUsuario(String id, String colorPrimario, String colorSecundario, String animacion, Documento icono, Menu menuDeNav) {
        this.id = id;
        this.colorPrimario = colorPrimario;
        this.colorSecundario = colorSecundario;
        this.animacion = animacion;
        this.icono = icono;
        this.menuDeNav = menuDeNav;
    }

    public String getId() {
        return id;
    }

    public String getColorPrimario() {
        return colorPrimario;
    }

    public String getColorSecundario() {
        return colorSecundario;
    }

    public String getAnimacion() {
        return animacion;
    }

    public Documento getIcono() {
        return icono;
    }

    public Menu getMenuDeNav() {
        return menuDeNav;
    }
}
