package com.tesis.persistencia.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DataMenu {

    @Id
    private String id;
    private List<DataOpcionMenu> opciones;

    public DataMenu(List<DataOpcionMenu> opciones) {
        this.opciones = opciones;
    }
}
