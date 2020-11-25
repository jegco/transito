package com.tesis.transito.presentacion.controladores;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.transito.entidades.Tipo;
import org.springframework.web.bind.annotation.*;
import reactor.core.CorePublisher;

@RestController
@RequestMapping("/tipos")
@CrossOrigin(value = {"http://localhost:4200", "http://localhost:4220"})
public class TiposControlador {

    private final CasoDeUso<String, Tipo> buscarTiposCasoDeUso;
    private final CasoDeUso<Tipo, Tipo> guardarTiposCasoDeUso;
    private final CasoDeUso<Tipo, Void> eliminarTiposCasoDeUso;

    public TiposControlador(CasoDeUso<String, Tipo> buscarTiposCasoDeUso,
                            CasoDeUso<Tipo, Tipo> guardarTiposCasoDeUso,
                            CasoDeUso<Tipo, Void> eliminarTiposCasoDeUso) {
        this.buscarTiposCasoDeUso = buscarTiposCasoDeUso;
        this.guardarTiposCasoDeUso = guardarTiposCasoDeUso;
        this.eliminarTiposCasoDeUso = eliminarTiposCasoDeUso;
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

    @DeleteMapping
    private CorePublisher<Void> eliminarTipo(@RequestBody Tipo tipo) {
        return eliminarTiposCasoDeUso.ejecutar(tipo);
    }
}
