package br.com.sias.api.controller;

import br.com.sias.api.dto.PresencaRequest;
import br.com.sias.api.dto.PresencaResponse;
import br.com.sias.api.model.Presenca;
import br.com.sias.api.model.Paciente;
import br.com.sias.api.repository.PresencaRepository;
import br.com.sias.api.repository.PacienteRepository;
import br.com.sias.api.service.PresencaService;
import br.com.sias.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presenca")
@CrossOrigin(origins = "*")
public class PresencaController {

    @Autowired
    private PresencaService service;

    @PostMapping
    public ResponseEntity<PresencaResponse> criar(@RequestBody PresencaRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<PresencaResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PresencaResponse> atualizar(@PathVariable Long id, @RequestBody PresencaRequest dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresencaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @GetMapping("/hoje")
    public List<Presenca> PresencaDoDiaAtual(){
        return service.PresencaDoDiaAtual();
    }
    @GetMapping("/paciente/{id}")
    public List<Presenca> historicoPresencaParciente(@PathVariable Long id){
        return service.historicoPresencaParciente(id);
    }
}