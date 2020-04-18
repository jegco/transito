package com.tesis.persistencia.utils;

import com.tesis.dominio.modelos.PuntoDeAtencion;
import com.tesis.persistencia.modelos.DataPuntoDeAtencion;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PuntoDeAtencionDataPuntoDeAtencionMapper implements
        Function<PuntoDeAtencion, DataPuntoDeAtencion> {
    @Override
    public DataPuntoDeAtencion apply(PuntoDeAtencion puntoDeAtencion) {
        return new DataPuntoDeAtencion(puntoDeAtencion.getId(),
                puntoDeAtencion.getNombre(),
                puntoDeAtencion.getLatitud(),
                puntoDeAtencion.getLongitud());
    }
}
