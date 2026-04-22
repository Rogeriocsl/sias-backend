package br.com.sias.api.service;

import br.com.sias.api.dto.PacienteRequest;
import br.com.sias.api.dto.PacienteResponse;
import br.com.sias.api.exception.NotFoundException;
import br.com.sias.api.model.Paciente;
import br.com.sias.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository repository;

    public PacienteResponse criar(PacienteRequest dto) {
        Paciente paciente = converterParaEntity(dto);
        Paciente salvo = repository.save(paciente);
        return converterParaResponse(salvo);
    }

    public PacienteResponse atualizar(Long id, PacienteRequest dto) {
        Paciente existente = repository.findById(id).orElseThrow(() -> new NotFoundException("Paciente não encontrado"));

        existente.setNome(dto.getNome());
        existente.setCpf(dto.getCpf());
        existente.setDataNascimento(dto.getDataNascimento());
        existente.setTelefone(dto.getTelefone());
        existente.setGenero(dto.getGenero());
        existente.setTipoSanguineo(dto.getTipoSanguineo());
        existente.setCondicoesSaude(dto.getCondicoesSaude());
        existente.setUnidadeOrigem(dto.getUnidadeOrigem());

        Paciente atualizado = repository.save(existente);

        return converterParaResponse(atualizado);
    }

    public List<Paciente> listar() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Paciente não encontrado");
        }
        repository.deleteById(id);
    }

    public PacienteResponse buscarPorId(Long id) {
        Paciente paciente = repository.findById(id).orElseThrow(() -> new NotFoundException("Paciente não encontrado"));
        return converterParaResponse(paciente);
    }

    private Paciente converterParaEntity(PacienteRequest dto) {
        Paciente paciente = new Paciente();

        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setTelefone(dto.getTelefone());
        paciente.setGenero(dto.getGenero());
        paciente.setTipoSanguineo(dto.getTipoSanguineo());
        paciente.setCondicoesSaude(dto.getCondicoesSaude());
        paciente.setUnidadeOrigem(dto.getUnidadeOrigem());

        return paciente;
    }

    private PacienteResponse converterParaResponse(Paciente paciente) {
        PacienteResponse resp = new PacienteResponse();

        resp.setId(paciente.getId());
        resp.setNome(paciente.getNome());
        resp.setCpf(paciente.getCpf());
        resp.setDataNascimento(paciente.getDataNascimento());
        resp.setTelefone(paciente.getTelefone());
        resp.setGenero(paciente.getGenero());
        resp.setTipoSanguineo(paciente.getTipoSanguineo());
        resp.setCondicoesSaude(paciente.getCondicoesSaude());
        resp.setUnidadeOrigem(paciente.getUnidadeOrigem());

        return resp;
    }
}