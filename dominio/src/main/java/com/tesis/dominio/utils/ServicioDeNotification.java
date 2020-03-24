package com.tesis.dominio.utils;

import com.tesis.dominio.modelos.Notificacion;
import com.tesis.dominio.modelos.Usuario;
import reactor.core.publisher.Flux;

public interface ServicioDeNotification {
    Flux<Boolean> enviarNotificacionAlUsuario(Notificacion notificacion);

    Flux<Usuario> enviarNotificacionAlAdmin(Notificacion notificacion);
}
