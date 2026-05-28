package br.com.sias.api.controller;

import br.com.sias.api.dto.UnidadeBasicaSaudeRequest;
import br.com.sias.api.dto.UnidadeBasicaSaudeResponse;
import br.com.sias.api.model.UnidadeBasicaSaude;
import br.com.sias.api.service.UnidadeBasicaSaudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidade")
@CrossOrigin(origins = "*")
public class UnidadeBasicaSaudeController {
    @Autowired
    private UnidadeBasicaSaudeService service;

    @PostMapping
    public ResponseEntity<UnidadeBasicaSaudeResponse> criar(@RequestBody UnidadeBasicaSaudeRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<UnidadeBasicaSaudeResponse>> listar() {
        List<UnidadeBasicaSaude> unidades = service.listar();
        List<UnidadeBasicaSaudeResponse> dtos = unidades.stream()
                .map(ubs -> {
                    UnidadeBasicaSaudeResponse resp = new UnidadeBasicaSaudeResponse();
                    resp.setId(ubs.getId());
                    resp.setNomeUnidade(ubs.getNomeUnidade());
                    resp.setEndereco(ubs.getEndereco());
                    resp.setBairro(ubs.getBairro());
                    resp.setNumero(ubs.getNumero());
                    resp.setNomeEnfermeiroResponsavel(ubs.getNomeEnfermeiroResponsavel());
                    return resp;
                })
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeBasicaSaudeResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeBasicaSaudeResponse> atualizar(@PathVariable Long id, @RequestBody UnidadeBasicaSaudeRequest dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}