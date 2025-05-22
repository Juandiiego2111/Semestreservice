package com.example.semestreservice.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record SemestreRequest(

        @NotBlank(message = "El nombre no puede estar vacío")
        @Pattern(regexp = "^\\d{4}-[1-2]$", message = "Formato de semestre inválido (ej: 2024-1)")
        String nombre,

        @NotNull(message = "La fecha de inicio es obligatoria")
        @FutureOrPresent(message = "La fecha de inicio debe ser presente o futura")
        LocalDate fechaInicio,

        @NotNull(message = "La fecha de fin es obligatoria")
        @Future(message = "La fecha de fin debe ser futura")
        LocalDate fechaFin,

        @NotNull(message = "Debe indicar si el programa se encuentra activo")
        boolean activo,

        @NotNull(message = "El id del programa es obligatorio")
        Long idPrograma

) {}
