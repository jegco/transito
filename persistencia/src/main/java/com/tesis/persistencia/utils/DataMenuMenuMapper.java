package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.Menu;
import com.tesis.dominio.modelos.OpcionMenu;
import com.tesis.persistencia.modelos.DataMenu;
import com.tesis.persistencia.modelos.DataOpcionMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class DataMenuMenuMapper implements Function<DataMenu, Menu> {

    private final DataOpcionMenuOpcionMenuMapper mapper;

    public DataMenuMenuMapper(DataOpcionMenuOpcionMenuMapper dataOpcionMenuOpcionMenuMapper) {
        this.mapper = dataOpcionMenuOpcionMenuMapper;
    }

    @Override
    public Menu apply(DataMenu dataMenu) {
        List<OpcionMenu> opciones = new ArrayList<>();
        for(DataOpcionMenu opcion: dataMenu.getOpciones()) {
            opciones.add(mapper.apply(opcion));
        }
        return new Menu(opciones);
    }
}
