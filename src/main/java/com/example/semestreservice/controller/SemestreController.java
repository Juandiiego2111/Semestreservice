package com.example.semestreservice.controller;

import com.example.semestreservice.dto.SemestreRequest;
import com.example.semestreservice.dto.SemestreResponse;
import com.example.semestreservice.exception.ResourceNotFoundException;
import com.example.semestreservice.service.IProgramaClient;
import com.example.semestreservice.service.SemestreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/semestre-service")
@RequiredArgsConstructor
public class SemestreController {

    private final SemestreService semestreService;
    private final IProgramaClient programaClient;

    @GetMapping("/semestres")
    public ResponseEntity<Map<String, Object>> listarSemestres() {
        List<SemestreResponse> lista = semestreService.listarSemestres();
        Map<String, Object> resp = new HashMap<>();

        if (lista.isEmpty()) {
            throw new ResourceNotFoundException("Semestres", "disponibles", "ninguno");
        }

        resp.put("message", "Semestres obtenidos exitosamente");
        resp.put("semestres", lista);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/semestre/page/{page}")
    public ResponseEntity<Map<String, Object>> listarSemestresPaginados(@PathVariable int page) {
        Map<String, Object> pageData = semestreService.listarSemestresPaginados(page);

        if (((List<?>) pageData.get("semestres")).isEmpty()) {
            throw new ResourceNotFoundException("Semestres", "página", page);
        }

        pageData.put("message", "Semestres paginados obtenidos exitosamente");
        return ResponseEntity.ok(pageData);
    }

    @PostMapping("/semestres")
    public ResponseEntity<Map<String, Object>> crearSemestre(
            @Valid @RequestBody SemestreRequest request) {

        validarPrograma(request.idPrograma());
        SemestreResponse creado = semestreService.crearSemestre(request);

        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Semestre creado exitosamente");
        resp.put("semestre", creado);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @GetMapping("/semestres/{id}")
    public ResponseEntity<Map<String, Object>> obtenerSemestre(@PathVariable Long id) {
        SemestreResponse dto = semestreService.obtenerSemestre(id);

        if (dto == null) {
            throw new ResourceNotFoundException("Semestre", "id", id);
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Semestre obtenido exitosamente");
        resp.put("semestre", dto);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/semestres/{id}")
    public ResponseEntity<Map<String, Object>> actualizarSemestre(
            @PathVariable Long id,
            @Valid @RequestBody SemestreRequest request) {

        validarPrograma(request.idPrograma());
        SemestreResponse updated = semestreService.actualizarSemestre(id, request);

        if (updated == null) {
            throw new ResourceNotFoundException("Semestre", "id", id);
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Semestre actualizado exitosamente");
        resp.put("semestre", updated);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/semestres/{id}")
    public ResponseEntity<Map<String, Object>> eliminarSemestre(@PathVariable Long id) {
        if (!semestreService.existeSemestre(id)) {
            throw new ResourceNotFoundException("Semestre", "id", id);
        }

        semestreService.eliminarSemestre(id);
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Semestre eliminado exitosamente");
        return ResponseEntity.ok(resp);
    }

    private void validarPrograma(Long programaId) {
        try {
            programaClient.obtenerProgramaPorId(programaId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Programa", "id", programaId);
        }
    }
}