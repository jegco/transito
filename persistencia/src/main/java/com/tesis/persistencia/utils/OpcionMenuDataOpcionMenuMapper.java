package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.OpcionMenu;
import com.tesis.persistencia.modelos.DataOpcionMenu;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class OpcionMenuDataOpcionMenuMapper implements Function<OpcionMenu, DataOpcionMenu> {
    @Override
    public DataOpcionMenu apply(OpcionMenu opcionMenu) {
        return new DataOpcionMenu(opcionMenu.getOpciones(), opcionMenu.getTitulo());
    }
}
