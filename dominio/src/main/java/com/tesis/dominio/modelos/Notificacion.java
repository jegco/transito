package com.tesis.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
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
