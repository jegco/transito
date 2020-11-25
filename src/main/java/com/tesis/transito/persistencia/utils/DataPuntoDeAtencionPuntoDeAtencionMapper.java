package com.tesis.transito.persistencia.utils;

import com.tesis.transito.entidades.PuntoDeAtencion;
import com.tesis.transito.entidades.DataPuntoDeAtencion;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DataPuntoDeAtencionPuntoDeAtencionMapper implements
        Function<DataPuntoDeAtencion, PuntoDeAtencion> {
    @Override
    public PuntoDeAtencion apply(DataPuntoDeAtencion dataPuntoDeAtencion) {
        return new PuntoDeAtencion(dataPuntoDeAtencion.getId(),
                dataPuntoDeAtencion.getNombre(),
                dataPuntoDeAtencion.getDireccion(),
                dataPuntoDeAtencion.getLatitud(),
                dataPuntoDeAtencion.getLongitud());
    }
}
