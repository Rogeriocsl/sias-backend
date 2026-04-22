package br.com.sias.api.service;

import br.com.sias.api.dto.AvaliacaoFisicaResponse;
import br.com.sias.api.dto.EncaminhamentoResponse;
import br.com.sias.api.dto.PacienteRequest;
import br.com.sias.api.dto.PacienteResponse;
import br.com.sias.api.exception.NotFoundException;
import br.com.sias.api.model.AvaliacaoFisica;
import br.com.sias.api.model.Encaminhamento;
import br.com.sias.api.model.Paciente;
import br.com.sias.api.repository.AvaliacaoFisicaRepository;
import br.com.sias.api.repository.EncaminhamentoRepository;
import br.com.sias.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository repository;

    @Autowired
    private EncaminhamentoRepository encaminhamentosRepository;

    @Autowired
    private AvaliacaoFisicaRepository avaliacoesRepository;



    public PacienteResponse BuscarComHistorico(Long id) {
        Paciente paciente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paciente não Encontrado"));

        PacienteResponse pacienteResponse = new PacienteResponse();

        pacienteResponse.setId(paciente.getId());
        pacienteResponse.setNome(paciente.getNome());
        pacienteResponse.setCpf(paciente.getCpf());
        pacienteResponse.setDataNascimento(paciente.getDataNascimento());
        pacienteResponse.setTelefone(paciente.getTelefone());
        pacienteResponse.setGenero(paciente.getGenero());
        pacienteResponse.setTipoSanguineo(paciente.getTipoSanguineo());
        pacienteResponse.setCondicoesSaude(
                paciente.getCondicoesSaude().stream()
                        .map(Enum::name)
                        .toList()
        );

        pacienteResponse.setEncaminhamentos(
                encaminhamentosRepository.findByPacienteId(id).stream()
                        .map(this::converterEncaminhamentoParaResponse)
                        .toList()
        );

        pacienteResponse.setAvaliacoes(
                avaliacoesRepository.findByPacienteId(id).stream()
                        .map(this::converterAvaliacaoParaResponse)
                        .toList()
        );

        return pacienteResponse;

    }


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
        resp.setCondicoesSaude(
                paciente.getCondicoesSaude().stream()
                        .map(Enum::name)
                        .toList()
        );

        return resp;
    }

    private EncaminhamentoResponse converterEncaminhamentoParaResponse(Encaminhamento e) {
        EncaminhamentoResponse r = new EncaminhamentoResponse();
        r.setId(e.getId());
        r.setPacienteId(e.getPaciente().getId());
        r.setMotivo(e.getMotivo().name());
        r.setStatus(e.getStatus().name());
        r.setDataEncaminhamento(e.getDataEncaminhamento());
        r.setObservacoes(e.getObservacoes());
        return r;
    }

    private AvaliacaoFisicaResponse converterAvaliacaoParaResponse(AvaliacaoFisica a) {
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
