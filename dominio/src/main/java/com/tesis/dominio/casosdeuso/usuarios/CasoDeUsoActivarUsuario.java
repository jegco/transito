package com.tesis.dominio.casosdeuso.usuarios;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.casosdeuso.params.UsuarioParams;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Notificacion;
import com.tesis.dominio.modelos.Usuario;
import com.tesis.dominio.utils.ServicioDeNotification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
                                new Notificacion(usuario, "registro exitosa", "registro autorizado por el administrador")));
    }
}
