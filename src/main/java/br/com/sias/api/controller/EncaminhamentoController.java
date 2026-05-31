package br.com.sias.api.controller;

import br.com.sias.api.dto.EncaminhamentoRequest;
import br.com.sias.api.dto.EncaminhamentoResponse;
import br.com.sias.api.model.enums.EncaminhamentoStatus;
import br.com.sias.api.service.EncaminhamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encaminhamentos")
public class EncaminhamentoController {

    @Autowired
    private EncaminhamentoService service;

    @GetMapping
    public ResponseEntity<List<EncaminhamentoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/status")
    public ResponseEntity<List<EncaminhamentoResponse>> listarPorStatus(
            @RequestParam EncaminhamentoStatus status) {
        return ResponseEntity.ok(service.listarEncaminhamentos(status));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<EncaminhamentoResponse>> listarPorPaciente(
            @PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.buscarEncaminhamento(pacienteId));
    }

    @PostMapping
    public ResponseEntity<EncaminhamentoResponse> criar(
            @RequestBody EncaminhamentoRequest dto) {
        return ResponseEntity.status(201).body(service.criar(dto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EncaminhamentoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody EncaminhamentoRequest dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}