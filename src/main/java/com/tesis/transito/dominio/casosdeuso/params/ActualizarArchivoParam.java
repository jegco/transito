package com.tesis.transito.dominio.casosdeuso.params;

import com.tesis.transito.entidades.Documento;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.codec.multipart.FilePart;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Setter
public class ActualizarArchivoParam{

    private Documento documento;
    private FilePart archivo;
}
