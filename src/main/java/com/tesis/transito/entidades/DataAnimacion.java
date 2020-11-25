package com.tesis.transito.entidades;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class DataAnimacion {

    @Id
    private String id;
    private String nombre;
    private int tiempoDeEspera;
    private int tiempoAnimacion;
}
