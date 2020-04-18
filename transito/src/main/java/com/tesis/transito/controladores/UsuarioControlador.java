package com.tesis.transito.controladores;

import com.tesis.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.dominio.casosdeuso.params.UsuarioParams;
import com.tesis.dominio.modelos.Usuario;
import com.tesis.dominio.utils.ServicioDeNotification;
import com.tesis.transito.mappers.UsuarioVistaUsuarioMapper;
import com.tesis.transito.mappers.VistaUsuarioUsuarioMapper;
import com.tesis.transito.modelos.AuthResponse;
import com.tesis.transito.modelos.VistaUsuario;
import com.tesis.transito.seguridad.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.CorePublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(value = {"http://localhost:4200"})
public class UsuarioControlador {

    private final CasoDeUso<UsuarioParams, Usuario> casoDeUsoLogin;
    private final CasoDeUso<Usuario, Usuario> casoDeUsoRegistrarUsuario;
    private final CasoDeUso<Usuario, Void> casoDeUsoEliminarUsuario;
    private final CasoDeUso<Usuario, Boolean> casoDeUsoActivarUsuario;
    private final UsuarioVistaUsuarioMapper usuarioVistaUsuarioMapper;
    private final VistaUsuarioUsuarioMapper vistaUsuarioUsuarioMapper;
    private final JWTUtil jwtUtil;

    @Autowired
    private ServicioDeNotification servicioDeNotification;

    public UsuarioControlador(CasoDeUso<UsuarioParams, Usuario> casoDeUsoLogin,
                              UsuarioVistaUsuarioMapper usuarioVistaUsuarioMapper,
                              CasoDeUso<Usuario, Usuario> casoDeUsoRegistrarUsuario,
                              CasoDeUso<Usuario, Boolean> casoDeUsoActivarUsuario,
                              CasoDeUso<Usuario, Void> casoDeUsoEliminarUsuario,
                              VistaUsuarioUsuarioMapper vistaUsuarioUsuarioMapper, JWTUtil jwtUtil) {
        this.casoDeUsoLogin = casoDeUsoLogin;
        this.usuarioVistaUsuarioMapper = usuarioVistaUsuarioMapper;
        this.casoDeUsoRegistrarUsuario = casoDeUsoRegistrarUsuario;
        this.vistaUsuarioUsuarioMapper = vistaUsuarioUsuarioMapper;
        this.casoDeUsoEliminarUsuario = casoDeUsoEliminarUsuario;
        this.casoDeUsoActivarUsuario = casoDeUsoActivarUsuario;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Mono<AuthResponse> login(@RequestBody UsuarioParams param) {
        return Mono.from(casoDeUsoLogin.ejecutar(param))
                .map(usuarioVistaUsuarioMapper)
                .map(usuario ->
                        new AuthResponse(jwtUtil.generateToken(usuario), usuario.getRole()));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Mono<VistaUsuario> registrarUsuario(@RequestBody VistaUsuario usuario) {
        return Mono.from(casoDeUsoRegistrarUsuario.ejecutar(vistaUsuarioUsuarioMapper.apply(usuario)))
                .map(usuarioVistaUsuarioMapper);
    }

    @DeleteMapping
    public CorePublisher<Void> eliminarUsuario(@RequestBody VistaUsuario usuario) {
        return casoDeUsoEliminarUsuario.ejecutar(vistaUsuarioUsuarioMapper.apply(usuario));
    }

    @GetMapping()
    public Flux<VistaUsuario> buscarUsuarios() {
        return Flux.from(casoDeUsoLogin.ejecutar(null))
                .map(usuarioVistaUsuarioMapper);
    }

    @PutMapping()
    public CorePublisher<Boolean> actviarUsuario(@RequestBody VistaUsuario usuario) {
        return casoDeUsoActivarUsuario.ejecutar(vistaUsuarioUsuarioMapper.apply(usuario));
    }
}
