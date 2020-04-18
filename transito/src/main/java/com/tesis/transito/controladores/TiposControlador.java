package com.tesis.transito.controladores;

import com.tesis.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.dominio.modelos.Tipo;
import org.springframework.web.bind.annotation.*;
import reactor.core.CorePublisher;

@RestController
@RequestMapping("/tipos")
@CrossOrigin(value = {"http://localhost:4200", "http://localhost:4220"})
public class TiposControlador {

    private final CasoDeUso<String, Tipo> buscarTiposCasoDeUso;
    private final CasoDeUso<Tipo, Tipo> guardarTiposCasoDeUso;

    public TiposControlador(CasoDeUso<String, Tipo> buscarTiposCasoDeUso, CasoDeUso<Tipo, Tipo> guardarTiposCasoDeUso) {
        this.buscarTiposCasoDeUso = buscarTiposCasoDeUso;
        this.guardarTiposCasoDeUso = guardarTiposCasoDeUso;
    }

    @GetMapping()
    public CorePublisher<Tipo> buscarTipos() {
        return buscarTiposCasoDeUso.ejecutar(null);
    }

    @GetMapping("/{nombre}")
    public CorePublisher<Tipo> buscarTipos(String nombre) {
        return buscarTiposCasoDeUso.ejecutar(nombre);
    }

    @PostMapping
    public CorePublisher<Tipo> guardarTipo(@RequestBody Tipo tipo) {
        return guardarTiposCasoDeUso.ejecutar(tipo);
    }
}
