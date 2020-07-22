package com.tesis.transito.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Notificacion {
    private Usuario para;
    private Usuario involucrado;
    private String asunto;
    private String mensaje;

    public Notificacion(Usuario para, String asunto, String mensaje) {
        this.para = para;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }
}
