package com.tesis.persistencia.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tipo")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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
