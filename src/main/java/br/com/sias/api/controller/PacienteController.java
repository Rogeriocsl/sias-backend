package br.com.sias.api.controller;

import br.com.sias.api.dto.PacienteRequest;
import br.com.sias.api.dto.PacienteResponse;
import br.com.sias.api.model.Paciente;
import br.com.sias.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*") // Permite que o Frontend acesse a API
public class PacienteController {

    @Autowired
    private PacienteService service;

    // 1. CADASTRAR UM NOVO PACIENTE
    @PostMapping
    public ResponseEntity<PacienteResponse> criar(@RequestBody PacienteRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    // 2. LISTAR TODOS OS PACIENTES
    @GetMapping
    public List<Paciente> listar() {
        return service.listar();
    }

    // 3. BUSCAR UM PACIENTE POR ID
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // 4. ATUALIZAR UM PACIENTE
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> atualizar(@PathVariable Long id, @RequestBody PacienteRequest dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // 5. DELETAR UM PACIENTE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}