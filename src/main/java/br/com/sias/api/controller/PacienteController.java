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
@CrossOrigin(origins = "*")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;


    @PostMapping
    public ResponseEntity<Paciente> criar(@Valid @RequestBody Paciente paciente) {
        Paciente salvo = repository.save(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }


    @GetMapping
    public List<Paciente> listar() {
        return repository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


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


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}