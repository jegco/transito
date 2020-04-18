package com.tesis.dominio.utils;

import com.tesis.dominio.modelos.Notificacion;
import com.tesis.dominio.modelos.Usuario;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServicioDeNotification {
    Mono<Boolean> enviarNotificacionAlUsuario(Notificacion notificacion);

    Mono<Usuario> enviarNotificacionAlAdmin(Notificacion notificacion);
}
