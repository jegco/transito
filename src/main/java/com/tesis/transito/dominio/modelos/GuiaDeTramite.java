package com.tesis.transito.dominio.modelos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Setter
public class GuiaDeTramite {
    private String id;
    private String titulo;
    private String descripcion;
    private Documento anexo;
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
