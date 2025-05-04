package com.example.semestreservice.service;

import com.example.semestreservice.dto.SemestreRequest;
import com.example.semestreservice.dto.SemestreResponse;
import com.example.semestreservice.exception.ResourceNotFoundException;
import com.example.semestreservice.model.Semestre;
import com.example.semestreservice.repository.SemestreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SemestreService {
    private final SemestreRepository semestreRepository;

    @Transactional
    public SemestreResponse crearSemestre(SemestreRequest request) {
        validarFechas(request);

        Semestre semestre = new Semestre();
        semestre.setNombre(request.nombre());
        semestre.setFechaInicio(request.fechaInicio());
        semestre.setFechaFin(request.fechaFin());
        semestre.setActivo(request.activo());

        semestre = semestreRepository.save(semestre);
        return mapToResponse(semestre);
    }

    @Transactional(readOnly = true)
    public List<SemestreResponse> listarSemestres() {
        return semestreRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SemestreResponse obtenerSemestre(Long id) {
        Semestre semestre = semestreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semestre", "id", id));
        return mapToResponse(semestre);
    }

    @Transactional
    public SemestreResponse actualizarSemestre(Long id, SemestreRequest request) {
        validarFechas(request);

        Semestre semestre = semestreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semestre", "id", id));

        semestre.setNombre(request.nombre());
        semestre.setFechaInicio(request.fechaInicio());
        semestre.setFechaFin(request.fechaFin());
        semestre.setActivo(request.activo());

        semestre = semestreRepository.save(semestre);
        return mapToResponse(semestre);
    }

    @Transactional
    public void eliminarSemestre(Long id) {
        if (!semestreRepository.existsById(id)) {
            throw new ResourceNotFoundException("Semestre", "id", id);
        }
        semestreRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> listarSemestresPaginados(int page) {
        int pageSize = 10; // tamaño de página, puedes parametrizar
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Semestre> pageResult = semestreRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("semestres", pageResult.getContent());
        response.put("currentPage", pageResult.getNumber());
        response.put("totalItems", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());

        return response;
    }

    private SemestreResponse mapToResponse(Semestre semestre) {
        return new SemestreResponse(
                semestre.getId(),
                semestre.getNombre(),
                semestre.getFechaInicio(),
                semestre.getFechaFin(),
                semestre.isActivo()
        );
    }

    private void validarFechas(SemestreRequest request) {
        if (request.fechaFin().isBefore(request.fechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
    }
}