package com.tesis.transito.persistencia.delegados;

import com.tesis.transito.dominio.casosdeuso.params.UsuarioParams;
import com.tesis.transito.dominio.delegado.Delegado;
import com.tesis.transito.dominio.modelos.Usuario;
import com.tesis.transito.persistencia.repositorios.UsuarioRepositorio;
import com.tesis.transito.persistencia.utils.DataUsuarioUsuarioMapper;
import com.tesis.transito.persistencia.utils.UsuarioDataUsuarioMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioDelegado implements Delegado<UsuarioParams, Usuario> {

    private UsuarioRepositorio repositorio;
    private DataUsuarioUsuarioMapper dataUsuarioUsuarioMapper;
    private UsuarioDataUsuarioMapper usuarioDataUsuarioMapper;

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
    public Flux<Usuario> crear(List<Usuario> entidades) {
        return repositorio.insert(entidades
                .stream()
                .map(usuarioDataUsuarioMapper).collect(Collectors.toList()))
                .map(dataUsuarioUsuarioMapper);
    }

    @Override
    public Mono<Usuario> actualizar(Usuario entidad) {
        return repositorio.findById(entidad.getId()).flatMap(usuario -> {
                entidad.setPassword(usuario.getPassword());
                return repositorio.save(usuarioDataUsuarioMapper.apply(entidad)).map(dataUsuarioUsuarioMapper);
        });
    }

    @Override
    public Flux<Usuario> buscar(UsuarioParams param) {
        if (param != null) {
            if (param.getRol() == null) {
                return repositorio.findDataUsuarioByNombreAndPasswordAndActive(
                        param.getNombreUsuario(),
                        param.getPassword(),
                        param.isActive()).map(dataUsuarioUsuarioMapper);
            } else if (!param.getRol().isEmpty()) {
                return repositorio.findDataUsuarioByActiveAndRol(param.isActive(), param.getRol())
                        .map(dataUsuarioUsuarioMapper);
            } else {
                return repositorio.findDataUsuarioByActive(param.isActive()).map(dataUsuarioUsuarioMapper);
            }
        }
        return repositorio.findAll().map(dataUsuarioUsuarioMapper);
    }

    @Override
    public Mono<Void> eliminar(Usuario entidad) {
        return repositorio.delete(usuarioDataUsuarioMapper.apply(entidad));
    }
}
