package com.tesis.transito.controladores;

import com.tesis.dominio.casosdeuso.base.CasoDeUso;
import com.tesis.dominio.casosdeuso.params.GuiaDeTramiteParams;
import com.tesis.dominio.modelos.GuiaDeTramite;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/guias")
public class GuiasDeTramiteControlador {

    private final CasoDeUso<GuiaDeTramiteParams, GuiaDeTramite> casoDeUsoBuscarGuiaDeTramite;
    private final CasoDeUso<GuiaDeTramite, GuiaDeTramite> casoDeUsoCrearGuiasDetramite;

    public GuiasDeTramiteControlador(CasoDeUso<GuiaDeTramiteParams, GuiaDeTramite> casoDeUsoBuscarGuiaDeTramite,
                                     CasoDeUso<GuiaDeTramite, GuiaDeTramite> casoDeUsoCrearGuiasDetramite) {
        this.casoDeUsoBuscarGuiaDeTramite = casoDeUsoBuscarGuiaDeTramite;
        this.casoDeUsoCrearGuiasDetramite = casoDeUsoCrearGuiasDetramite;
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
    public Flux<GuiaDeTramite> buscarGuiasDeTramite() {
        return casoDeUsoBuscarGuiaDeTramite.ejecutar(null);
    }

    @PostMapping
    public Flux<GuiaDeTramite> crearGuiaDeTramite(@RequestBody GuiaDeTramite guiaDeTramite) {
        return casoDeUsoCrearGuiasDetramite.ejecutar(guiaDeTramite);
    }
}
