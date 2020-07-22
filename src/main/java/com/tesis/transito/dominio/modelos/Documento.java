package com.tesis.transito.dominio.modelos;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Builder
@EqualsAndHashCode
public class Documento {

    private String id;
    private String nombre;
    private String archivo;
    private String extension;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private String rutaDeDescarga;
}
