package br.com.sias.api.service;

import br.com.sias.api.dto.AgendamentoRequest;
import br.com.sias.api.dto.AgendamentoResponse;
import br.com.sias.api.exception.BadRequestException;
import br.com.sias.api.exception.NotFoundException;
import br.com.sias.api.model.Agendamento;
import br.com.sias.api.model.Paciente;
import br.com.sias.api.model.Usuario;
import br.com.sias.api.model.enums.StatusAgendamento;
import br.com.sias.api.repository.AgendamentoRepository;
import br.com.sias.api.repository.PacienteRepository;
import br.com.sias.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository repository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public AgendamentoResponse criar(AgendamentoRequest dto) {
        if (dto.getDataHora().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Data inválida: não é possível agendar no passado.");
        }

        if (repository.existsByInstrutorIdAndDataHora(dto.getInstrutorId(), dto.getDataHora())) {
            throw new BadRequestException("Este instrutor já possui um agendamento neste horário.");
        }

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado."));

        Usuario instrutor = usuarioRepository.findById(dto.getInstrutorId())
                .orElseThrow(() -> new NotFoundException("Instrutor não encontrado."));

        Agendamento agendamento = new Agendamento();
        agendamento.setPaciente(paciente);
        agendamento.setInstrutor(instrutor);
        agendamento.setDataHora(dto.getDataHora());
        agendamento.setObservacao(dto.getObservacao());
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        Agendamento salvo = repository.save(agendamento);
        return new AgendamentoResponse(salvo);
    }
    public List<AgendamentoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(AgendamentoResponse::new)
                .collect(Collectors.toList());
    }

    public AgendamentoResponse buscarPorId(Long id) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado"));
        return new AgendamentoResponse(agendamento);
    }

    public AgendamentoResponse atualizar(Long id, AgendamentoRequest dto) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado."));

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado."));

        Usuario instrutor = usuarioRepository.findById(dto.getInstrutorId())
                .orElseThrow(() -> new NotFoundException("Instrutor não encontrado."));

        agendamento.setPaciente(paciente);
        agendamento.setInstrutor(instrutor);
        agendamento.setDataHora(dto.getDataHora());
        agendamento.setObservacao(dto.getObservacao());

        if (dto.getStatus() != null) {
            agendamento.setStatus(dto.getStatus());
        }
        return new AgendamentoResponse(repository.save(agendamento));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Agendamento não encontrado.");
        }
        repository.deleteById(id);
    }
}