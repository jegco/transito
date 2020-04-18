package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.PuntoDeAtencion;
import com.tesis.persistencia.modelos.DataPuntoDeAtencion;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DataPuntoDeAtencionPuntoDeAtencionMapper implements
        Function<DataPuntoDeAtencion, PuntoDeAtencion> {
    @Override
    public PuntoDeAtencion apply(DataPuntoDeAtencion dataPuntoDeAtencion) {
        return new PuntoDeAtencion(dataPuntoDeAtencion.getId(),
                dataPuntoDeAtencion.getNombre(),
                dataPuntoDeAtencion.getLatitud(),
                dataPuntoDeAtencion.getLongitud());
    }
}
