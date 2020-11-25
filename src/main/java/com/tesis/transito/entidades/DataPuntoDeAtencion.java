package com.tesis.transito.entidades;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("PuntoDeAtencion")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@EqualsAndHashCode
@Getter
public class DataPuntoDeAtencion {

    @Id
    private String id;
    private String nombre;
    private String direccion;
    private String latitud;
    private String longitud;

    public DataPuntoDeAtencion(String nombre, String direccion, String latitud, String longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
    }
}
