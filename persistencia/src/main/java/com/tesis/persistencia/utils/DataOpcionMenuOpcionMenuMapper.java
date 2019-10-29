package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.OpcionMenu;
import com.tesis.persistencia.modelos.DataOpcionMenu;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DataOpcionMenuOpcionMenuMapper implements Function<DataOpcionMenu, OpcionMenu> {
    @Override
    public OpcionMenu apply(DataOpcionMenu dataOpcionMenu) {
        return new OpcionMenu(dataOpcionMenu.getOpciones(), dataOpcionMenu.getTitulo());
    }
}
