package br.com.sias.api.controller;

import br.com.sias.api.model.Paciente;
import br.com.sias.api.repository.PacienteRepository;
import jakarta.validation.Valid;
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
    private PacienteRepository repository;

    // 1. CADASTRAR UM NOVO PACIENTE
    @PostMapping
    public ResponseEntity<Paciente> criar(@Valid @RequestBody Paciente paciente) {
        Paciente salvo = repository.save(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // 2. LISTAR TODOS OS PACIENTES
    @GetMapping
    public List<Paciente> listar() {
        return repository.findAll();
    }

    // 3. BUSCAR UM PACIENTE POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. ATUALIZAR UM PACIENTE
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable Long id, @Valid @RequestBody Paciente dados) {
        return repository.findById(id)
                .map(paciente -> {
                    paciente.setNome(dados.getNome());
                    paciente.setTelefone(dados.getTelefone());
                    paciente.setGenero(dados.getGenero());
                    paciente.setCondicoesSaude(dados.getCondicoesSaude());

                    return ResponseEntity.ok(repository.save(paciente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETAR UM PACIENTE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}