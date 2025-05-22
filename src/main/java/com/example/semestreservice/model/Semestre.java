package com.example.semestreservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "semestres")
@Getter
@Setter
public class Semestre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El semestre debe estar asociado a un programa académico")
    @Column(name = "id_programa", nullable = false)
    private Long idPrograma;

    @NotBlank(message = "El nombre del semestre no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    @NotNull(message = "Debe ingresar el número del semestre")
    @Range(min = 1, max = 10, message = "El semestre debe estar en un rango del 1 al 10")
    @Column(name = "numero_semestre", nullable = false)
    private Integer numeroSemestre;

    @NotNull(message = "Debe ingresar la fecha de inicio del semestre")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "Debe ingresar la fecha de fin del semestre")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @NotNull(message = "Debe indicar si el semestre se encuentra activo")
    @Column(nullable = false)
    private Boolean activo;
}
