package com.example.semestreservice.service;

import com.example.semestreservice.model.ProgramaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "programa-service")
public interface IProgramaClient {

    @GetMapping("/api/v1/programa-service/programas")
    Map<String, List<ProgramaDTO>> obtenerProgramas();
}
