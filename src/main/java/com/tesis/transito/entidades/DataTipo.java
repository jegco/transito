package com.tesis.transito.entidades;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tipo")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class DataTipo {
    @Id
    private String id;
    private String nombre;
    private String icono;

    public DataTipo(String nombre, String icono) {
        this.nombre = nombre;
        this.icono = icono;
    }
}
