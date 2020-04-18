package com.tesis.persistencia.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "guias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DataGuiaDeTramite {

    @Id
    private String id;
    private String titulo;
    private String descripcion;
    private List<String> formularios;
    private String tipo;
    private String soporteLegal;
    private List<String> puntosDeAtencion;
    private List<DataPaso> dataPasos;

    public DataGuiaDeTramite(String titulo, String descripcion, List<String> formularios,
                             List<DataPaso> dataPasos, String tipo, String soporteLegal,
                             List<String> puntosDeAtencion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.formularios = formularios;
        this.dataPasos = dataPasos;
        this.tipo = tipo;
        this.soporteLegal = soporteLegal;
        this.puntosDeAtencion = puntosDeAtencion;
    }
}
