package com.tesis.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class GuiaDeTramite {
    private String id;
    private String titulo;
    private String descripcion;
    private List<Documento> formularios;
    private List<Paso> pasos;
    private String soporteLegal;
    private List<PuntoDeAtencion> puntosDeAtencion;
    private Tipo tipo;

    public GuiaDeTramite(String id, String titulo, String descripcion, List<Paso> pasos, String soporteLegal) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.pasos = pasos;
        this.soporteLegal = soporteLegal;
    }
}
