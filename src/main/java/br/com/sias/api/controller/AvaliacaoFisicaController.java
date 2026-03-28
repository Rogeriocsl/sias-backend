package br.com.sias.api.controller;

import br.com.sias.api.model.AvaliacaoFisica;
import br.com.sias.api.repository.AvaliacaoFisicaRepository;
import jakarta.validation.Valid;
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
    private AvaliacaoFisicaRepository repository;

    // 1. CRIA UMA NOVA AVALIAÇÃO
    @PostMapping
    public ResponseEntity<AvaliacaoFisica> criar(@Valid @RequestBody AvaliacaoFisica avaliacao) {
        AvaliacaoFisica salvo = repository.save(avaliacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // 2. LISTAR TODAS AS AVALIAÇÕES
    @GetMapping
    public List<AvaliacaoFisica> Listar() {
        return repository.findAll();
    }

    // 3. BUSCAR UMA AVALIAÇÃO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoFisica> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. ATUALIZAR UMA AVALIAÇÃO
    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoFisica> atualizar(@PathVariable Long id, @Valid @RequestBody AvaliacaoFisica avaliacao) {
        return repository.findById(id)
                .map(existente -> {
                    avaliacao.setId(id);
                    AvaliacaoFisica atualizado = repository.save(avaliacao);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETAR UMA AVALIAÇÃO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}