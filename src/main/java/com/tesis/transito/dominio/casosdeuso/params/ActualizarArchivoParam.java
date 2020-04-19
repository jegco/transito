package com.tesis.transito.dominio.casosdeuso.params;

import com.tesis.transito.dominio.modelos.Documento;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

public class ActualizarArchivoParam extends ArchivoParam {

    private Documento documento;

    public ActualizarArchivoParam(String nombre, Flux<DataBuffer> archivo, Documento documento) {
        super(nombre, archivo);
        this.documento = documento;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
}
