package com.tesis.transito.dominio.utils;

import com.tesis.transito.dominio.modelos.Notificacion;
import com.tesis.transito.dominio.modelos.Usuario;
import reactor.core.publisher.Mono;

public interface ServicioDeNotification {
    Mono<Boolean> enviarNotificacionAlUsuario(Notificacion notificacion);

    Mono<Usuario> enviarNotificacionAlAdmin(Notificacion notificacion);
}
