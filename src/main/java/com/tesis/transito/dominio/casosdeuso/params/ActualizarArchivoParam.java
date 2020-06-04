package com.tesis.transito.dominio.casosdeuso.params;

import com.tesis.transito.dominio.modelos.Documento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@Getter
@Setter
public class ActualizarArchivoParam{

    private Documento documento;
    private FilePart archivo;
}
