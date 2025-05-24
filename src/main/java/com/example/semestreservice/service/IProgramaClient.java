package com.example.semestreservice.service;

import com.example.semestreservice.model.ProgramaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "programa-service",
        path = "/api/v1"

)
public interface IProgramaClient {

    @GetMapping("/programas")
    Map<String, List<ProgramaDTO>> obtenerTodosLosProgramas();

    @GetMapping("/programas/{id}")
    ProgramaDTO obtenerProgramaPorId(@PathVariable Long id);

    @GetMapping("/programas/existe/{id}")
    Boolean existePrograma(@PathVariable Long id);
}