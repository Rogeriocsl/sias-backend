package br.com.sias.api.controller;

import br.com.sias.api.dto.DashboardEstatisticasResponse;
import br.com.sias.api.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/admin")
    public ResponseEntity<DashboardEstatisticasResponse> getEstatisticasAdmin() {
        return ResponseEntity.ok(dashboardService.obterEstatisticasAvancadas());
    }
}