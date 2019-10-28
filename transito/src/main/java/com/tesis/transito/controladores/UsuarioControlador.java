package com.tesis.transito.controladores;

import com.tesis.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.dominio.casosdeuso.params.LoginParam;
import com.tesis.dominio.modelos.Usuario;
import com.tesis.transito.mappers.UsuarioVistaUsuarioMapper;
import com.tesis.transito.mappers.VistaUsuarioUsuarioMapper;
import com.tesis.transito.modelos.VistaUsuario;
import com.tesis.transito.seguridad.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final CasoDeUso<LoginParam, Usuario> casoDeUsoLogin;
    private final CasoDeUso<Usuario, Usuario> casoDeUsoRegistrarUsuario;
    private final UsuarioVistaUsuarioMapper usuarioVistaUsuarioMapper;
    private final VistaUsuarioUsuarioMapper vistaUsuarioUsuarioMapper;
    @Autowired private JWTUtil jwtUtil;

    public UsuarioControlador(CasoDeUso<LoginParam, Usuario> casoDeUsoLogin,
                              UsuarioVistaUsuarioMapper usuarioVistaUsuarioMapper,
                              CasoDeUso<Usuario, Usuario> casoDeUsoRegistrarUsuario,
                              VistaUsuarioUsuarioMapper vistaUsuarioUsuarioMapper) {
        this.casoDeUsoLogin = casoDeUsoLogin;
        this.usuarioVistaUsuarioMapper = usuarioVistaUsuarioMapper;
        this.casoDeUsoRegistrarUsuario = casoDeUsoRegistrarUsuario;
        this.vistaUsuarioUsuarioMapper = vistaUsuarioUsuarioMapper;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Mono<ResponseEntity<String>> login(@RequestBody LoginParam param) {
        return Mono.from(casoDeUsoLogin.ejecutar(param)
                .map(usuarioVistaUsuarioMapper))
                .map(usuario -> ResponseEntity.ok(jwtUtil.generateToken(usuario)))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @RequestMapping(method = RequestMethod.POST)
    public Mono<VistaUsuario> registrarUsuario(@RequestBody VistaUsuario usuario) {
        return Mono.from(
                casoDeUsoRegistrarUsuario.ejecutar(vistaUsuarioUsuarioMapper.apply(usuario)))
                .map(usuarioVistaUsuarioMapper);
    }
}
