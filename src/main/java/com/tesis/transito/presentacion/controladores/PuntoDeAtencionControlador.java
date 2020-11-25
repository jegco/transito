package com.tesis.transito.presentacion.controladores;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.transito.entidades.PuntoDeAtencion;
import org.springframework.web.bind.annotation.*;
import reactor.core.CorePublisher;

import java.util.List;

@RestController
@RequestMapping("/puntoDeAtencion")
@CrossOrigin(value = {"http://localhost:4200", "http://localhost:4220"})
public class PuntoDeAtencionControlador {

    private final CasoDeUso<String, PuntoDeAtencion> casoDeUsoBuscarPuntosDeAtencion;
    private final CasoDeUso<List<PuntoDeAtencion>, PuntoDeAtencion> casoDeUsoGuardarPuntoDeAtencion;
    private final CasoDeUso<PuntoDeAtencion, Void> casoDeUsoEliminarPuntoDeAtencion;

    public PuntoDeAtencionControlador(CasoDeUso<String, PuntoDeAtencion> casoDeUsoBuscarPuntosDeAtencion,
                                      CasoDeUso<List<PuntoDeAtencion>, PuntoDeAtencion> casoDeUsoGuardarPuntoDeAtencion,
                                      CasoDeUso<PuntoDeAtencion, Void> casoDeUsoEliminarPuntoDeAtencion) {
        this.casoDeUsoBuscarPuntosDeAtencion = casoDeUsoBuscarPuntosDeAtencion;
        this.casoDeUsoGuardarPuntoDeAtencion = casoDeUsoGuardarPuntoDeAtencion;
        this.casoDeUsoEliminarPuntoDeAtencion = casoDeUsoEliminarPuntoDeAtencion;
    }

    @GetMapping()
    public CorePublisher<PuntoDeAtencion> buscarPuntosDeAtencion() {
        return casoDeUsoBuscarPuntosDeAtencion.ejecutar(null);
    }

    @GetMapping("/{nombre}")
    public CorePublisher<PuntoDeAtencion> buscarPuntosDeAtencion(@RequestParam String nombre) {
        return casoDeUsoBuscarPuntosDeAtencion.ejecutar(nombre);
    }

    @PostMapping()
    public CorePublisher<PuntoDeAtencion> guardarPuntoDeAtencion(@RequestBody List<PuntoDeAtencion> puntoDeAtencion) {
        return casoDeUsoGuardarPuntoDeAtencion.ejecutar(puntoDeAtencion);
    }

    @DeleteMapping()
    public CorePublisher<Void> eliminarPuntoDeAtencion(@RequestBody PuntoDeAtencion puntoDeAtencion) {
        return casoDeUsoEliminarPuntoDeAtencion.ejecutar(puntoDeAtencion);
    }
}
