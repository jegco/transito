package com.tesis.transito.dominio.casosdeuso.tipos;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.entidades.Tipo;
import com.tesis.transito.dominio.utils.ServicioDeAlmacenamiento;
import org.springframework.stereotype.Service;
import reactor.core.CorePublisher;

@Service
public class EliminarTipoCasoDeUso extends CasoDeUsoImpl<Tipo, Void> {

    private final Delegado<String, Tipo> delegado;
    private final ServicioDeAlmacenamiento servicioDeAlmacenamiento;

    public EliminarTipoCasoDeUso(Delegado<String, Tipo> delegado, ServicioDeAlmacenamiento servicioDeAlmacenamiento) {
        this.delegado = delegado;
        this.servicioDeAlmacenamiento = servicioDeAlmacenamiento;
    }

    @Override
    protected CorePublisher<Void> construirCasoDeUso(Tipo tipo) {
        return servicioDeAlmacenamiento.eliminarArchivo(tipo.getIcono().getNombre())
                .flatMap(ok -> delegado.eliminar(tipo));
    }
}
