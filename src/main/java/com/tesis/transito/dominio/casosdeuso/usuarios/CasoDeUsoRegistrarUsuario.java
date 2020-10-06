package com.tesis.transito.dominio.casosdeuso.usuarios;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.transito.dominio.casosdeuso.params.UsuarioParams;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.dominio.modelos.Notificacion;
import com.tesis.transito.dominio.modelos.Usuario;
import com.tesis.transito.dominio.utils.ServicioDeNotification;
import com.tesis.transito.dominio.utils.exceptions.UserAlreadyExistException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CasoDeUsoRegistrarUsuario extends CasoDeUsoImpl<Usuario, Usuario> {

    private final Delegado<UsuarioParams, Usuario> usuarioDelegado;
    private final ServicioDeNotification servicioDeNotification;

    public CasoDeUsoRegistrarUsuario(Delegado<UsuarioParams, Usuario> usuarioDelegado, ServicioDeNotification servicioDeNotification) {
        this.usuarioDelegado = usuarioDelegado;
        this.servicioDeNotification = servicioDeNotification;
    }

    @Override
    protected Flux<Usuario> construirCasoDeUso(Usuario usuario) {
        return usuarioDelegado.buscar(new UsuarioParams(usuario.getNombreDeUsuario(), usuario.getPassword()))
                .switchMap(usuarioRegistrado -> Flux.error(new UserAlreadyExistException("usuario ya registrado")))
                .switchIfEmpty(Flux.from(usuarioDelegado.crear(usuario)))
                .switchMap(usuarioNuevo -> usuarioDelegado.buscar(new UsuarioParams("SUPER_ADMIN", true)))
                .switchMap(usuarioANotificar -> servicioDeNotification
                        .enviarNotificacionAlAdmin(new Notificacion(usuarioANotificar,
                                usuario,
                                "Confirmacion de registro de nuevo usuario",
                                "por favor haga click en el mensaje para confirmar la entrada del nuevo usuario")));
    }
}
