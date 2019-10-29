package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.Menu;
import com.tesis.dominio.modelos.OpcionMenu;
import com.tesis.persistencia.modelos.DataMenu;
import com.tesis.persistencia.modelos.DataOpcionMenu;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.function.Function;

@Component
public class MenuDataMenuMapper implements Function<Menu, DataMenu> {

    private final OpcionMenuDataOpcionMenuMapper mapper;

    public MenuDataMenuMapper(OpcionMenuDataOpcionMenuMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public DataMenu apply(Menu menu) {
        ArrayList<DataOpcionMenu> opciones = new ArrayList<>();
        for (OpcionMenu opcion: menu.getOpciones()) {
            opciones.add(mapper.apply(opcion));
        }
        return new DataMenu(opciones);
    }
}
