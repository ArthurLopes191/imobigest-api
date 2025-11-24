package com.pds.ImobiGest.controller;

import com.pds.ImobiGest.dto.dashboard.DashboardDTO;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/{idImobiliaria}")
    public ResponseEntity<DashboardDTO> getDashboard(@PathVariable Integer idImobiliaria) throws RegraDeNegocioException {
        DashboardDTO dashboard = dashboardService.getDashboardData(idImobiliaria);
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/periodo/{idImobiliaria}")
    public ResponseEntity<DashboardDTO> getDashboardPorPeriodo(
            @PathVariable Integer idImobiliaria,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) throws RegraDeNegocioException {

        DashboardDTO dashboard = dashboardService.getDashboardDataPorPeriodo(idImobiliaria, dataInicio, dataFim);
        return ResponseEntity.ok(dashboard);
    }
}