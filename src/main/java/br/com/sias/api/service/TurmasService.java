package br.com.sias.api.service;

import br.com.sias.api.dto.TurmasRequest;
import br.com.sias.api.dto.TurmasResponse;
import br.com.sias.api.exception.NotFoundException;
import br.com.sias.api.model.Turmas;
import br.com.sias.api.repository.TurmasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmasService {
    @Autowired
    private TurmasRepository repository;

    public TurmasResponse criar(TurmasRequest dto) {
        Turmas turmas = converterParaEntity(dto);
        Turmas salvo = repository.save(turmas);
        return converterParaResponse(salvo);
    }

    public TurmasResponse atualizar(Long id, TurmasRequest dto) {
        Turmas existente = repository.findById(id).orElseThrow(() -> new NotFoundException("Turma não encontrada"));

        existente.setNome(dto.getNome());
        existente.setEducador(dto.getEducador());
        existente.setQuantidadeAlunos(dto.getQuantidadeAlunos());

        Turmas atualizado = repository.save(existente);

        return converterParaResponse(atualizado);
    }

    public List<Turmas> listar() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Turma não encontrada");
        }
        repository.deleteById(id);
    }

    public TurmasResponse buscarPorId(Long id) {
        Turmas turmas = repository.findById(id).orElseThrow(() -> new NotFoundException("Turma não encontrada"));
        return converterParaResponse(turmas);
    }

    private Turmas converterParaEntity(TurmasRequest dto) {
        Turmas turmas = new Turmas();

        turmas.setNome(dto.getNome());
        turmas.setEducador(dto.getEducador());
        turmas.setQuantidadeAlunos(dto.getQuantidadeAlunos());

        return turmas;
    }

    private TurmasResponse converterParaResponse(Turmas turmas) {
        TurmasResponse resp = new TurmasResponse();

        resp.setId(turmas.getId());
        resp.setNome(turmas.getNome());
        resp.setEducador(turmas.getEducador());
        resp.setQuantidadeAlunos(turmas.getQuantidadeAlunos());

        return resp;
    }
}