package com.tesis.persistencia.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "documentos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DataDocumento {
    @Id
    private String id;
    private String nombre;
    private String archivo;
    private String extension;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private String rutaDeDescarga;

    public DataDocumento(String nombre, String archivo, LocalDate fechaCreacion, LocalDate fechaActualizacion,
                         String rutaDeDescarga, String extension) {
        this.nombre = nombre;
        this.archivo = archivo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.rutaDeDescarga = rutaDeDescarga;
        this.extension = extension;
    }
}
