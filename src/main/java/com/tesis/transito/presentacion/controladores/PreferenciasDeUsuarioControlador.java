package com.tesis.transito.presentacion.controladores;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.transito.entidades.PreferenciasDeUsuario;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/preferencias")
@CrossOrigin(value = {"http://localhost:4200"})

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
    public Mono<PreferenciasDeUsuario> crearOModificarPreferenciasDeUsuario(@RequestBody PreferenciasDeUsuario preferenciasDeUsuario) {
        return Mono.from(casoDeUsoCrearOModificarPreferenciasDeUsuario.ejecutar(preferenciasDeUsuario));
    }
}
