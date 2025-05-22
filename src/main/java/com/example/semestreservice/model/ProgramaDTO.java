package com.example.semestreservice.model;

import lombok.Data;

@Data
public class ProgramaDTO {
    private Long id;
    private boolean activo;
    private String descripcion;
    private Long duracion;
    private Long idCoordinador;
    private Long idFacultad;
    private String nivelAcademico;
    private String nombre;
    private Byte numeroCreditos;
    private String perfilEgreso;
}
