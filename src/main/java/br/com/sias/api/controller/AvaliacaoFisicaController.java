package br.com.sias.api.controller;

import br.com.sias.api.dto.AvaliacaoFisicaRequest;
import br.com.sias.api.dto.AvaliacaoFisicaResponse;
import br.com.sias.api.model.AvaliacaoFisica;
import br.com.sias.api.model.Paciente;
import br.com.sias.api.repository.AvaliacaoFisicaRepository;
import br.com.sias.api.repository.PacienteRepository;
import br.com.sias.api.service.AvaliacaoFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
@CrossOrigin(origins = "*")
public class AvaliacaoFisicaController {

    @Autowired
    private AvaliacaoFisicaService service;

    @PostMapping
    public ResponseEntity<AvaliacaoFisicaResponse> criar(@RequestBody AvaliacaoFisicaRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoFisicaResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoFisicaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoFisicaResponse> atualizar(@PathVariable Long id, @RequestBody AvaliacaoFisicaRequest dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<AvaliacaoFisicaResponse> buscarPorPaciente(@PathVariable Long pacienteId) {
        return service.buscarPorPaciente(pacienteId);
    }
}