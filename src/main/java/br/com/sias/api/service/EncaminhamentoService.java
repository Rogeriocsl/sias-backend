package br.com.sias.api.service;

import br.com.sias.api.dto.EncaminhamentoRequest;
import br.com.sias.api.dto.EncaminhamentoResponse;
import br.com.sias.api.model.Encaminhamento;
import br.com.sias.api.model.Paciente;
import br.com.sias.api.model.enums.EncaminhamentoMotivo;
import br.com.sias.api.model.enums.EncaminhamentoStatus;
import br.com.sias.api.repository.EncaminhamentoRepository;
import br.com.sias.api.repository.PacienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EncaminhamentoService {
    
    @Autowired
    private EncaminhamentoRepository repository;

    @Autowired
    private PacienteRepository pacienteRepository;

    private EncaminhamentoResponse converterParaResponse(Encaminhamento encaminhamento){
        EncaminhamentoResponse response = new EncaminhamentoResponse();
        response.setId(encaminhamento.getId());
        response.setPacienteId(encaminhamento.getPaciente().getId());
        response.setObservacoes(encaminhamento.getObservacoes());
        response.setMotivo(encaminhamento.getMotivo().name());
        response.setDataEncaminhamento(encaminhamento.getDataEncaminhamento());
        response.setStatus(encaminhamento.getStatus().name());
        return response;
    }

    private Encaminhamento converterParaEntity(EncaminhamentoRequest dto){
        Encaminhamento encaminhamento = new Encaminhamento();
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId()).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        encaminhamento.setPaciente(paciente);
        encaminhamento.setMotivo(EncaminhamentoMotivo.valueOf(dto.getMotivo()));
        encaminhamento.setDataEncaminhamento(dto.getDataEncaminhamento());
        encaminhamento.setStatus(EncaminhamentoStatus.PENDENTE);
        encaminhamento.setObservacoes(dto.getObservacoes());
        return encaminhamento;

    }

    public EncaminhamentoResponse criar(EncaminhamentoRequest dto){
        Encaminhamento encaminhamento = converterParaEntity(dto);
        Encaminhamento salvo = repository.save(encaminhamento);
        return converterParaResponse(salvo);
    }

    public List<Encaminhamento> listar(){
        return repository.findAll();
    }

    public EncaminhamentoResponse atualizar(Long id, EncaminhamentoRequest dto){
        Encaminhamento encaminhamento = repository.findById(id).orElseThrow(() -> new RuntimeException("Encaminhamento não encontrado"));
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId()).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        encaminhamento.setPaciente(paciente);
        encaminhamento.setMotivo(EncaminhamentoMotivo.valueOf(dto.getMotivo()));
        encaminhamento.setDataEncaminhamento(dto.getDataEncaminhamento());
        encaminhamento.setStatus(EncaminhamentoStatus.PENDENTE);
        encaminhamento.setObservacoes(dto.getObservacoes());

        Encaminhamento atualizado = repository.save(encaminhamento);
        return converterParaResponse(atualizado);
    }

    public List<EncaminhamentoResponse> buscarEncaminhamento(Long pacienteId) {
        return repository.findByPacienteIdOrderByDataEncaminhamentoDesc(pacienteId).stream().map(this::converterParaResponse).toList();
    }

    public List<EncaminhamentoResponse> listarEncaminhamentos(EncaminhamentoStatus status){
        return repository.findByStatus(status).stream().map(this::converterParaResponse).toList();
    }

    public void deletar(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Encaminhamento não encontrado");
            }
        repository.deleteById(id);
    }

}
