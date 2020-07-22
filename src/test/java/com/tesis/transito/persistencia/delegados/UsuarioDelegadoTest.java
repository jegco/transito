package com.tesis.transito.persistencia.delegados;

import com.sun.tools.javac.util.List;
import com.tesis.transito.dominio.casosdeuso.params.UsuarioParams;
import com.tesis.transito.dominio.modelos.Usuario;
import com.tesis.transito.persistencia.modelos.DataUsuario;
import com.tesis.transito.persistencia.repositorios.UsuarioRepositorio;
import com.tesis.transito.persistencia.utils.DataUsuarioUsuarioMapper;
import com.tesis.transito.persistencia.utils.UsuarioDataUsuarioMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class UsuarioDelegadoTest {

    @MockBean
    private UsuarioRepositorio repositorio;
    @SpyBean
    private DataUsuarioUsuarioMapper dataUsuarioUsuarioMapper;
    @SpyBean
    private UsuarioDataUsuarioMapper usuarioDataUsuarioMapper;

    private UsuarioDelegado delegado;

    @Captor
    private ArgumentCaptor<DataUsuario> dataUsuarioCaptor;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;

    @BeforeEach
    public void setup() {
        DataUsuario dataUsuario = new DataUsuario(null,
                "jorge",
                "qwerty",
                "jegco13@gmail.com",
                "3192098062",
                "SUPER_ADMIN", false);

        DataUsuario dataUsuarioConId = new DataUsuario("1",
                "jorge",
                "qwerty",
                "jegco13@gmail.com",
                "3192098062",
                "SUPER_ADMIN", false);

        List<DataUsuario> dataUsuarios = List.of(dataUsuario);

        when(repositorio.
                findDataUsuarioByNombreAndPasswordAndActive("jorge", "qwerty", false))
                .thenReturn(Flux.just(dataUsuario));
        when(repositorio.insert(dataUsuario)).thenReturn(Mono.just(dataUsuario));
        when(repositorio.save(dataUsuarioConId)).thenReturn(Mono.just(dataUsuarioConId));
        when(repositorio.insert(dataUsuarios)).thenReturn(Flux.fromIterable(dataUsuarios));
        when(repositorio.findById("1")).thenReturn(Mono.just(dataUsuarioConId));
        when(repositorio.delete(dataUsuarioConId)).thenReturn(Mono.empty());

        delegado = new UsuarioDelegado(repositorio, dataUsuarioUsuarioMapper, usuarioDataUsuarioMapper);
    }

    @Test
    public void buscar() {
        StepVerifier.
                create(delegado.buscar(new UsuarioParams("jorge", "qwerty")))
                .expectNextMatches(usuario -> usuario.getNombreDeUsuario().equals("jorge") && usuario.getRole().equals("SUPER_ADMIN"))
                .expectComplete()
                .verify();

        verify(dataUsuarioUsuarioMapper).apply(dataUsuarioCaptor.capture());
        DataUsuario usuario = dataUsuarioCaptor.getValue();
        Assertions.assertEquals("jorge", usuario.getNombre());
    }

    @Test
    void crear() {
        StepVerifier.
                create(delegado.crear(new Usuario(null,
                        "jorge",
                        "qwerty",
                        "jegco13@gmail.com",
                        "3192098062",
                        "SUPER_ADMIN", false)))
                .expectNextMatches(usuario -> usuario.getNombreDeUsuario().equals("jorge"))
                .expectComplete()
                .verify();

        verify(usuarioDataUsuarioMapper).apply(usuarioCaptor.capture());
        verify(dataUsuarioUsuarioMapper).apply(dataUsuarioCaptor.capture());
        DataUsuario usuario = dataUsuarioCaptor.getValue();
        Assertions.assertEquals("jorge", usuario.getNombre());
    }

    @Test
    void crearVarios() {
        StepVerifier.
                create(delegado.crear(List.of(new Usuario(null,
                        "jorge",
                        "qwerty",
                        "jegco13@gmail.com",
                        "3192098062",
                        "SUPER_ADMIN", false))))
                .expectNextMatches(usuario -> usuario.getNombreDeUsuario().equals("jorge"))
                .expectComplete()
                .verify();

        verify(usuarioDataUsuarioMapper).apply(usuarioCaptor.capture());
        verify(dataUsuarioUsuarioMapper).apply(dataUsuarioCaptor.capture());
        DataUsuario usuario = dataUsuarioCaptor.getValue();
        Assertions.assertEquals("jorge", usuario.getNombre());
    }

    @Test
    void actualizar() {
        StepVerifier.
                create(delegado.actualizar(new Usuario("1",
                        "jorge",
                        "qwerty",
                        "jegco13@gmail.com",
                        "3192098062",
                        "SUPER_ADMIN", false)))
                .expectNextMatches(usuario -> usuario.getNombreDeUsuario().equals("jorge"))
                .expectComplete()
                .verify();

        verify(usuarioDataUsuarioMapper).apply(usuarioCaptor.capture());
        verify(dataUsuarioUsuarioMapper).apply(dataUsuarioCaptor.capture());
        DataUsuario usuario = dataUsuarioCaptor.getValue();
        Assertions.assertEquals("jorge", usuario.getNombre());
    }

    @Test
    void eliminar() {
        StepVerifier.
                create(delegado.eliminar(new Usuario("1",
                        "jorge",
                        "qwerty",
                        "jegco13@gmail.com",
                        "3192098062",
                        "SUPER_ADMIN", false)))
                .expectComplete()
                .verify();

        verify(usuarioDataUsuarioMapper).apply(usuarioCaptor.capture());
        Usuario usuario = usuarioCaptor.getValue();
        Assertions.assertEquals("jorge", usuario.getNombreDeUsuario());
    }
}