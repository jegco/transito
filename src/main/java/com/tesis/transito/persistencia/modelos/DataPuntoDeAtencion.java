package com.tesis.transito.persistencia.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("PuntoDeAtencion")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DataPuntoDeAtencion {

    @Id
    private String id;
    private String nombre;
    private String latitud;
    private String longitud;

    public DataPuntoDeAtencion(String nombre, String latitud, String longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
