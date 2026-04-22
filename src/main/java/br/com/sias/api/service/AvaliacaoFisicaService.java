package br.com.sias.api.service;

import br.com.sias.api.dto.AvaliacaoFisicaRequest;
import br.com.sias.api.dto.AvaliacaoFisicaResponse;
import br.com.sias.api.model.AvaliacaoFisica;
import br.com.sias.api.model.Paciente;
import br.com.sias.api.repository.AvaliacaoFisicaRepository;
import br.com.sias.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoFisicaService {
    @Autowired
    private AvaliacaoFisicaRepository repository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public AvaliacaoFisicaResponse criar(AvaliacaoFisicaRequest dto) {
        AvaliacaoFisica avaliacao = converterParaEntity(dto);
        AvaliacaoFisica salvo = repository.save(avaliacao);
        return converterParaResponseAvaliacao(salvo);
    }

    public AvaliacaoFisicaResponse atualizar(Long id, AvaliacaoFisicaRequest dto) {
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

        return converterParaResponseAvaliacao(atualizado);
    }

    public List<AvaliacaoFisica> listar() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Avaliação não encontrada");
        }
        repository.deleteById(id);
    }

    public List<AvaliacaoFisicaResponse> buscarPorPaciente(Long pacienteId) {
        return repository.findByPacienteIdOrderByDataAvaliacaoDesc(pacienteId).stream().map(this::converterParaResponseAvaliacao).toList();
    }

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

    private AvaliacaoFisicaResponse converterParaResponseAvaliacao(AvaliacaoFisica a) {
        AvaliacaoFisicaResponse r = new AvaliacaoFisicaResponse();
        r.setId(a.getId());
        r.setPacienteId(a.getPaciente().getId());
        r.setDataAvaliacao(a.getDataAvaliacao());
        r.setPeso(a.getPeso());
        r.setAltura(a.getAltura());
        r.setImc(a.getImc());
        r.setPressaoArterial(a.getPressaoArterial());
        r.setFrequenciaCardiaca(a.getFrequenciaCardiaca());
        r.setCircunferenciaAbdominal(a.getCircunferenciaAbdominal());
        r.setObservacoes(a.getObservacoes());
        return r;
    }
}