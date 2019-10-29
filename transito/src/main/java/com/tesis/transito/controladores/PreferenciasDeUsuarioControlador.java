package com.tesis.transito.controladores;

import com.tesis.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.dominio.modelos.PreferenciasDeUsuario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/preferencias")
public class PreferenciasDeUsuarioControlador {

    private final CasoDeUso<String, PreferenciasDeUsuario> casoDeUsoBuscarPreferenciasDeUsuario;
    private final CasoDeUso<PreferenciasDeUsuario, PreferenciasDeUsuario>
            casoDeUsoCrearOModificarPreferenciasDeUsuario;

    public PreferenciasDeUsuarioControlador(CasoDeUso<String, PreferenciasDeUsuario> casoDeUsoBuscarPreferenciasDeUsuario,
                                            CasoDeUso<PreferenciasDeUsuario, PreferenciasDeUsuario> casoDeUsoCrearOModificarPreferenciasDeUsuario) {
        this.casoDeUsoBuscarPreferenciasDeUsuario = casoDeUsoBuscarPreferenciasDeUsuario;
        this.casoDeUsoCrearOModificarPreferenciasDeUsuario = casoDeUsoCrearOModificarPreferenciasDeUsuario;
    }

    @GetMapping
    public Mono<PreferenciasDeUsuario> buscarPreferenciasDeUsuario() {
        return Mono.from(casoDeUsoBuscarPreferenciasDeUsuario.ejecutar(null));
    }

    @PostMapping
    public Mono<PreferenciasDeUsuario> crearOModificarPreferenciasDeUsuario(PreferenciasDeUsuario preferenciasDeUsuario) {
        return Mono.from(casoDeUsoCrearOModificarPreferenciasDeUsuario.ejecutar(preferenciasDeUsuario));
    }
}
