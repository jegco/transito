package com.tesis.transito.dominio.casosdeuso.documentos;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.casosdeuso.params.ActualizarArchivoParam;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.dominio.modelos.Documento;
import com.tesis.transito.dominio.utils.ServicioDeAlmacenamiento;
import javafx.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class CasoDeUsoModificarDocumento extends CasoDeUsoImpl<ActualizarArchivoParam, Documento> {

    private final ServicioDeAlmacenamiento servicioDeAlmacenamiento;
    private final Delegado<String, Documento> delegado;

    public CasoDeUsoModificarDocumento(ServicioDeAlmacenamiento servicioDeAlmacenamiento, Delegado<String, Documento> delegado) {
        this.servicioDeAlmacenamiento = servicioDeAlmacenamiento;
        this.delegado = delegado;
    }

    @Override
    protected Mono<Documento> construirCasoDeUso(ActualizarArchivoParam archivoParam) {
        return servicioDeAlmacenamiento.
                guardarDocumento(archivoParam.getArchivo(), archivoParam.getNombre())
                .map(rutaArchivo -> new Pair<>(rutaArchivo, rutaArchivo.substring(rutaArchivo.lastIndexOf(".") + 1)))
                .flatMap(descripcionArchivo ->
                        delegado.crear(
                                new Documento(null,
                                        archivoParam.getNombre(),
                                        descripcionArchivo.getKey(),
                                        descripcionArchivo.getValue(),
                                        LocalDate.now(),
                                        LocalDate.now(),
                                        descripcionArchivo.getValue())));
    }
}
