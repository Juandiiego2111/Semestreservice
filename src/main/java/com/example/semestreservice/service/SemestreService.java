package com.example.semestreservice.service;

import com.example.semestreservice.dto.SemestreRequest;
import com.example.semestreservice.dto.SemestreResponse;
import com.example.semestreservice.exception.ResourceNotFoundException;
import com.example.semestreservice.model.Semestre;
import com.example.semestreservice.repository.SemestreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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