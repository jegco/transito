package com.tesis.dominio.casosdeuso.usuarios;

import com.tesis.dominio.casosdeuso.base.CasoDeUsoImpl;
import com.tesis.dominio.casosdeuso.params.LoginParam;
import com.tesis.dominio.delegado.Delegado;
import com.tesis.dominio.modelos.Usuario;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

public class CasoDeUsoLogin extends CasoDeUsoImpl<LoginParam, Usuario> {

    @Autowired private Delegado<LoginParam, Usuario> delegado;

    @Override
    protected Flux<Usuario> construirCasoDeUso(LoginParam param) {
        return delegado.buscar(param);
    }
}
