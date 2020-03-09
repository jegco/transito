package com.tesis.transito.controladores;

import com.tesis.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.dominio.modelos.GuiaDeTramite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/guias")
@CrossOrigin(value = {"http://localhost:4200", "http://localhost:4220"})
public class GuiasDeTramiteControlador {

    private final CasoDeUso<GuiaDeTramiteParams, GuiaDeTramite> casoDeUsoBuscarGuiaDeTramite;
    private final CasoDeUso<GuiaDeTramite, GuiaDeTramite> casoDeUsoCrearGuiasDetramite;
    private final CasoDeUso<GuiaDeTramite, Void> casoDeUsoEliminarGuiaDeTramite;

    public GuiasDeTramiteControlador(CasoDeUso<GuiaDeTramiteParams, GuiaDeTramite> casoDeUsoBuscarGuiaDeTramite,
                                     CasoDeUso<GuiaDeTramite, GuiaDeTramite> casoDeUsoCrearGuiasDetramite,
                                     CasoDeUso<GuiaDeTramite, Void> casoDeUsoEliminarGuiaDeTramite) {
        this.casoDeUsoBuscarGuiaDeTramite = casoDeUsoBuscarGuiaDeTramite;
        this.casoDeUsoCrearGuiasDetramite = casoDeUsoCrearGuiasDetramite;
        this.casoDeUsoEliminarGuiaDeTramite = casoDeUsoEliminarGuiaDeTramite;

    }

    @GetMapping("/{tipo}")
    public Flux<GuiaDeTramite> buscarGuiasDeTramitePorTipo(@PathVariable String tipo) {
        return casoDeUsoBuscarGuiaDeTramite.ejecutar(new GuiaDeTramiteParams(tipo));
    }

    @GetMapping("/{tipo}/{titulo}/{descripcion}")
    public Flux<GuiaDeTramite> buscarGuiasDeTramite(@PathVariable String tipo,
                                                    @PathVariable String titulo,
                                                    @PathVariable String descripcion) {
        return casoDeUsoBuscarGuiaDeTramite.ejecutar(new GuiaDeTramiteParams(titulo, descripcion, tipo));
    }

    @GetMapping
    public Flux<GuiaDeTramite> buscarGuiasDeTramite(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size
    ) {
        return casoDeUsoBuscarGuiaDeTramite.ejecutar(null).skip(page*size).limitRate(size);
    }

    @GetMapping("/titulo/{titulo}")
    public Mono<GuiaDeTramite> buscarGuiasDeTramitePorTitulo(@PathVariable String titulo) {
        return Mono.from(casoDeUsoBuscarGuiaDeTramite.ejecutar(new GuiaDeTramiteParams(titulo, null, null)));
    }

    @PostMapping
    public Flux<GuiaDeTramite> crearGuiaDeTramite(@RequestBody GuiaDeTramite guiaDeTramite) {
        return casoDeUsoCrearGuiasDetramite.ejecutar(guiaDeTramite);
    }

    @DeleteMapping()
    public Mono<Void> eliminarGuiaDeTramite(@RequestBody GuiaDeTramite guiaDeTramite) {
        return Mono.from(casoDeUsoEliminarGuiaDeTramite.ejecutar(guiaDeTramite));
    }
}
