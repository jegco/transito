package com.tesis.transito.persistencia.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DataAnimacion {

    @Id
    private String id;
    private String nombre;
    private int tiempoDeEspera;
    private int tiempoAnimacion;
}
