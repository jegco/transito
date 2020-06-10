package com.tesis.transito.dominio.casosdeuso.usuarios;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.casosdeuso.params.UsuarioParams;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.dominio.modelos.Notificacion;
import com.tesis.transito.dominio.modelos.Usuario;
import com.tesis.transito.dominio.utils.ServicioDeNotification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CasoDeUsoActivarUsuario extends CasoDeUsoImpl<Usuario, Boolean> {

    private final Delegado<UsuarioParams, Usuario> usuarioDelegado;
    private final ServicioDeNotification servicioDeNotification;

    public CasoDeUsoActivarUsuario(Delegado<UsuarioParams, Usuario> usuarioDelegado,
                                   ServicioDeNotification servicioDeNotification) {
        this.usuarioDelegado = usuarioDelegado;
        this.servicioDeNotification = servicioDeNotification;
    }

    @Override
    protected Mono<Boolean> construirCasoDeUso(Usuario usuario) {
        return usuarioDelegado.actualizar(usuario)
                .flatMap(usuarioActivado -> servicioDeNotification
                        .enviarNotificacionAlUsuario(
                                new Notificacion(usuario, "registro exitoso", "registro autorizado por el administrador")));
    }
}
