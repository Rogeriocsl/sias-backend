package br.com.sias.api.controller;

import br.com.sias.api.dto.AvaliacaoFisicaRequest;
import br.com.sias.api.dto.AvaliacaoFisicaResponse;
import br.com.sias.api.model.AvaliacaoFisica;
import br.com.sias.api.model.Paciente;
import br.com.sias.api.repository.AvaliacaoFisicaRepository;
import br.com.sias.api.repository.PacienteRepository;
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
    private PacienteRepository pacienteRepository;

    @Autowired
    private AvaliacaoFisicaRepository repository;

    // 1. CRIA UMA NOVA AVALIAÇÃO
    @PostMapping
    public ResponseEntity<AvaliacaoFisicaResponse> criar(@RequestBody AvaliacaoFisicaRequest dto) {
        AvaliacaoFisica avaliacao = converterParaEntity(dto);
        AvaliacaoFisica salvo = repository.save(avaliacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaResponse(salvo));
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
    public ResponseEntity<AvaliacaoFisicaResponse> atualizar(@PathVariable Long id, @RequestBody AvaliacaoFisicaRequest dto) {
        AvaliacaoFisica existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId()).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        existente.setPaciente(paciente);
        existente.setPeso(dto.getPeso());
        existente.setAltura(dto.getAltura());
        existente.setPressaoArterial(dto.getPressaoArterial());
        existente.setFrequenciaCardiaca(dto.getFrequenciaCardiaca());
        existente.setCircunferenciaAbdominal(dto.getCircunferenciaAbdominal());
        existente.setObservacoes(dto.getObservacoes());

        AvaliacaoFisica atualizado = repository.save(existente);

        return ResponseEntity.ok(converterParaResponse(atualizado));
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

    // 6. LISTAR POR PACIENTE
    @GetMapping("/paciente/{pacienteId}")
    public List<AvaliacaoFisicaResponse> listarPorPaciente(@PathVariable Long pacienteId) {
        return repository.findByPacienteIdOrderByDataAvaliacaoDesc(pacienteId)
                .stream().map(this::converterParaResponse).toList();
    }

    // 7. CONVERTER DTO PARA ENTITY
    private AvaliacaoFisica converterParaEntity(AvaliacaoFisicaRequest dto) {
        AvaliacaoFisica avaliacao = new AvaliacaoFisica();
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId()).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        avaliacao.setPaciente(paciente);
        avaliacao.setPeso(dto.getPeso());
        avaliacao.setAltura(dto.getAltura());
        avaliacao.setPressaoArterial(dto.getPressaoArterial());
        avaliacao.setFrequenciaCardiaca(dto.getFrequenciaCardiaca());
        avaliacao.setCircunferenciaAbdominal(dto.getCircunferenciaAbdominal());
        avaliacao.setObservacoes(dto.getObservacoes());

        return avaliacao;
    }

    // 8. CONVERTER DTO PARA RESPONSE
    private AvaliacaoFisicaResponse converterParaResponse(AvaliacaoFisica avaliacao) {
        AvaliacaoFisicaResponse resp = new AvaliacaoFisicaResponse();

        resp.setId(avaliacao.getId());
        resp.setPacienteId(avaliacao.getPaciente().getId());
        resp.setDataAvaliacao(avaliacao.getDataAvaliacao());
        resp.setPeso(avaliacao.getPeso());
        resp.setAltura(avaliacao.getAltura());
        resp.setImc(avaliacao.getImc());
        resp.setPressaoArterial(avaliacao.getPressaoArterial());
        resp.setFrequenciaCardiaca(avaliacao.getFrequenciaCardiaca());
        resp.setCircunferenciaAbdominal(avaliacao.getCircunferenciaAbdominal());
        resp.setObservacoes(avaliacao.getObservacoes());

        return resp;
    }
}