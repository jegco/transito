package com.tesis.transito.persistencia.modelos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Setter
public class DataPaso {
    private String titulo;
    private String descripcion;
    private String anexo;
}
