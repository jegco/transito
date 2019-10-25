package com.tesis.persistencia.delegados;

import com.tesis.dominio.casosdeuso.params.LoginParam;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Usuario;
import com.tesis.persistencia.repositorios.UsuarioRepositorio;
import com.tesis.persistencia.utils.DataUsuarioUsuarioMapper;
import com.tesis.persistencia.utils.UsuarioDataUsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UsuarioDelegado implements Delegado<LoginParam, Usuario> {

    private final UsuarioRepositorio repositorio;
    @Autowired private DataUsuarioUsuarioMapper dataUsuarioUsuarioMapper;
    @Autowired private UsuarioDataUsuarioMapper usuarioDataUsuarioMapper;

    @Autowired
    public UsuarioDelegado(UsuarioRepositorio repositorio,
                           DataUsuarioUsuarioMapper dataUsuarioUsuarioMapper,
                           UsuarioDataUsuarioMapper usuarioDataUsuarioMapper) {
        this.repositorio = repositorio;
        this.dataUsuarioUsuarioMapper = dataUsuarioUsuarioMapper;
        this.usuarioDataUsuarioMapper = usuarioDataUsuarioMapper;
    }

    @Override
    public Mono<Usuario> crear(Usuario entidad) {
        return repositorio.insert(usuarioDataUsuarioMapper.apply(entidad))
                .map(dataUsuarioUsuarioMapper);
    }

    @Override
    public Mono<Usuario> actualizar(Usuario entidad) {
        return repositorio.save(usuarioDataUsuarioMapper.apply(entidad)).map(dataUsuarioUsuarioMapper);
    }

    @Override
    public Flux<Usuario> buscar(LoginParam param) {
        return param == null ? repositorio.findAll().map(dataUsuarioUsuarioMapper):
                repositorio.findDataUsuarioByNombreDeUsuarioAndPassword(param.getNombreUsuario(), param.getContrasena());
    }

    @Override
    public Mono<Void> eliminar(Usuario entidad) {
        return repositorio.delete(usuarioDataUsuarioMapper.apply(entidad));
    }
}
