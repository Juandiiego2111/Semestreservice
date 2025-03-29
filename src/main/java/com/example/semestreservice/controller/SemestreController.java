package com.example.semestreservice.controller;

import com.example.semestreservice.dto.SemestreRequest;
import com.example.semestreservice.dto.SemestreResponse;
import com.example.semestreservice.service.SemestreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semestres")
@RequiredArgsConstructor
public class SemestreController {
    private final SemestreService semestreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SemestreResponse crearSemestre(@Valid @RequestBody SemestreRequest request) {
        return semestreService.crearSemestre(request);
    }

    @GetMapping
    public List<SemestreResponse> listarSemestres() {
        return semestreService.listarSemestres();
    }

    @GetMapping("/{id}")
    public SemestreResponse obtenerSemestre(@PathVariable Long id) {
        return semestreService.obtenerSemestre(id);
    }

    @PutMapping("/{id}")
    public SemestreResponse actualizarSemestre(
            @PathVariable Long id,
            @Valid @RequestBody SemestreRequest request) {
        return semestreService.actualizarSemestre(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarSemestre(@PathVariable Long id) {
        semestreService.eliminarSemestre(id);
    }

}