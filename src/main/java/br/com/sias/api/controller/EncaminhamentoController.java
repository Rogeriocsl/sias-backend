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

    /** Lista todos — usado pelo componente Encaminhamentos.jsx */
    @GetMapping
    public ResponseEntity<List<EncaminhamentoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    /** Lista por status — ex: GET /api/encaminhamentos/status?status=PENDENTE */
    @GetMapping("/status")
    public ResponseEntity<List<EncaminhamentoResponse>> listarPorStatus(
            @RequestParam EncaminhamentoStatus status) {
        return ResponseEntity.ok(service.listarEncaminhamentos(status));
    }

    /** Lista por paciente — ex: GET /api/encaminhamentos/paciente/1 */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<EncaminhamentoResponse>> listarPorPaciente(
            @PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.buscarEncaminhamento(pacienteId));
    }

    /** Cria novo encaminhamento */
    @PostMapping
    public ResponseEntity<EncaminhamentoResponse> criar(
            @RequestBody EncaminhamentoRequest dto) {
        return ResponseEntity.status(201).body(service.criar(dto));
    }

    /**
     * Atualiza turma, status e observações.
     * Usado pelo botão Salvar do componente Encaminhamentos.jsx.
     */
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