package com.tesis.transito.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Documento {

    private String id;
    private String nombre;
    private String archivo;
    private String extension;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private String rutaDeDescarga;
}
