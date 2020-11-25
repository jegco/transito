package com.tesis.transito.entidades;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "guias")
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class DataGuiaDeTramite {

    @Id
    private String id;
    private String titulo;
    private String descripcion;
    private String anexo;
    private String tipo;
    private String soporteLegal;
    private List<String> puntosDeAtencion;
    private List<DataPaso> dataPasos;

    public DataGuiaDeTramite(String titulo, String descripcion, String anexo,
                             List<DataPaso> dataPasos, String tipo, String soporteLegal,
                             List<String> puntosDeAtencion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.anexo = anexo;
        this.dataPasos = dataPasos;
        this.tipo = tipo;
        this.soporteLegal = soporteLegal;
        this.puntosDeAtencion = puntosDeAtencion;
    }
}
