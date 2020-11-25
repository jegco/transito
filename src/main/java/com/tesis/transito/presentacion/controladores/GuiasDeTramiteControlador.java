package com.tesis.transito.presentacion.controladores;

import com.tesis.transito.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.transito.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.transito.entidades.GuiaDeTramite;
import org.springframework.web.bind.annotation.*;
import reactor.core.CorePublisher;

@RestController
@RequestMapping("/guias")
@CrossOrigin(value = {"http://localhost:4200", "http://localhost:4300"})
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
    public CorePublisher<GuiaDeTramite> buscarGuiasDeTramitePorTipo(@PathVariable String tipo) {
        return casoDeUsoBuscarGuiaDeTramite.ejecutar(new GuiaDeTramiteParams(tipo));
    }

    @GetMapping("/{tipo}/{titulo}/{descripcion}")
    public CorePublisher<GuiaDeTramite> buscarGuiasDeTramite(@PathVariable String tipo,
                                                             @PathVariable String titulo,
                                                             @PathVariable String descripcion) {
        return casoDeUsoBuscarGuiaDeTramite.ejecutar(new GuiaDeTramiteParams(titulo, descripcion, tipo));
    }

    @GetMapping
    public CorePublisher<GuiaDeTramite> buscarGuiasDeTramite() {
        return casoDeUsoBuscarGuiaDeTramite.ejecutar(null);
    }

    @GetMapping("/titulo/{titulo}")
    public CorePublisher<GuiaDeTramite> buscarGuiasDeTramite(@PathVariable String titulo) {
        return casoDeUsoBuscarGuiaDeTramite.ejecutar(new GuiaDeTramiteParams(titulo, null, null));
    }

    @PostMapping
    public CorePublisher<GuiaDeTramite> crearGuiaDeTramite(@RequestBody GuiaDeTramite guiaDeTramite) {
        return casoDeUsoCrearGuiasDetramite.ejecutar(guiaDeTramite);
    }

    @DeleteMapping()
    public CorePublisher<Void> eliminarGuiaDeTramite(@RequestBody GuiaDeTramite guiaDeTramite) {
        return casoDeUsoEliminarGuiaDeTramite.ejecutar(guiaDeTramite);
    }
}
