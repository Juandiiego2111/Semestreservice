package com.example.semestreservice.controller;

import com.example.semestreservice.dto.SemestreRequest;
import com.example.semestreservice.dto.SemestreResponse;
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

    // Listar todos los semestres
    @GetMapping("/semestres")
    public ResponseEntity<Map<String, Object>> listarSemestres() {
        List<SemestreResponse> lista = semestreService.listarSemestres();
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Semestres obtenidos exitosamente");
        resp.put("semestres", lista);
        return ResponseEntity.ok(resp);
    }

    // Paginación de semestres
    @GetMapping("/semestre/page/{page}")
    public ResponseEntity<Map<String, Object>> listarSemestresPaginados(@PathVariable int page) {
        Map<String, Object> pageData = semestreService.listarSemestresPaginados(page);
        pageData.put("message", "Semestres paginados obtenidos exitosamente");
        return ResponseEntity.ok(pageData);
    }

    // Crear un nuevo semestre
    @PostMapping("/semestres")
    public ResponseEntity<Map<String, Object>> crearSemestre(
            @Valid @RequestBody SemestreRequest request) {
        SemestreResponse creado = semestreService.crearSemestre(request);
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Semestre creado exitosamente");
        resp.put("semestre", creado);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    // Obtener un semestre por ID
    @GetMapping("/semestres/{id}")
    public ResponseEntity<Map<String, Object>> obtenerSemestre(@PathVariable Long id) {
        SemestreResponse dto = semestreService.obtenerSemestre(id);
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Semestre obtenido exitosamente");
        resp.put("semestre", dto);
        return ResponseEntity.ok(resp);
    }

    // Actualizar un semestre existente
    @PutMapping("/semestres/{id}")
    public ResponseEntity<Map<String, Object>> actualizarSemestre(
            @PathVariable Long id,
            @Valid @RequestBody SemestreRequest request) {
        SemestreResponse updated = semestreService.actualizarSemestre(id, request);
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Semestre actualizado exitosamente");
        resp.put("semestre", updated);
        return ResponseEntity.ok(resp);
    }

    // Eliminar un semestre por ID
    @DeleteMapping("/semestres/{id}")
    public ResponseEntity<Map<String, Object>> eliminarSemestre(@PathVariable Long id) {
        semestreService.eliminarSemestre(id);
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Semestre eliminado exitosamente");
        return ResponseEntity.ok(resp);
    }
}
