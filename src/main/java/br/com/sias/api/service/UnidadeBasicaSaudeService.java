package br.com.sias.api.service;

import br.com.sias.api.dto.UnidadeBasicaSaudeRequest;
import br.com.sias.api.dto.UnidadeBasicaSaudeResponse;
import br.com.sias.api.exception.NotFoundException;
import br.com.sias.api.model.UnidadeBasicaSaude;
import br.com.sias.api.repository.UnidadeBasicaSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeBasicaSaudeService {
    @Autowired
    private UnidadeBasicaSaudeRepository repository;

    public UnidadeBasicaSaudeResponse criar(UnidadeBasicaSaudeRequest dto) {
        UnidadeBasicaSaude ubs = converterParaEntity(dto);
        UnidadeBasicaSaude salvo = repository.save(ubs);
        return converterParaResponse(salvo);
    }

    public UnidadeBasicaSaudeResponse atualizar(Long id, UnidadeBasicaSaudeRequest dto) {
        UnidadeBasicaSaude existente = repository.findById(id).orElseThrow(() -> new NotFoundException("Unidade Básica de Saúde não encontrada"));

        existente.setNomeUnidade(dto.getNomeUnidade());
        existente.setEndereco(dto.getEndereco());
        existente.setBairro(dto.getBairro());
        existente.setNumero(dto.getNumero());
        existente.setNomeEnfermeiroResponsavel(dto.getNomeEnfermeiroResponsavel());

        UnidadeBasicaSaude atualizado = repository.save(existente);

        return converterParaResponse(atualizado);
    }

    public List<UnidadeBasicaSaude> listar() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Unidade Básica de Saúde não encontrada");
        }
        repository.deleteById(id);
    }

    public UnidadeBasicaSaudeResponse buscarPorId(Long id) {
        UnidadeBasicaSaude ubs = repository.findById(id).orElseThrow(() -> new NotFoundException("Unidade Básica de Saúde não encontrada"));
        return converterParaResponse(ubs);
    }

    private UnidadeBasicaSaude converterParaEntity(UnidadeBasicaSaudeRequest dto) {
        UnidadeBasicaSaude ubs = new UnidadeBasicaSaude();

        ubs.setNomeUnidade(dto.getNomeUnidade());
        ubs.setEndereco(dto.getEndereco());
        ubs.setBairro(dto.getBairro());
        ubs.setNumero(dto.getNumero());
        ubs.setNomeEnfermeiroResponsavel(dto.getNomeEnfermeiroResponsavel());

        return ubs;
    }

    private UnidadeBasicaSaudeResponse converterParaResponse(UnidadeBasicaSaude ubs) {
        UnidadeBasicaSaudeResponse resp = new UnidadeBasicaSaudeResponse();

        resp.setId(ubs.getId());
        resp.setNomeUnidade(ubs.getNomeUnidade());
        resp.setEndereco(ubs.getEndereco());
        resp.setBairro(ubs.getBairro());
        resp.setNumero(ubs.getNumero());
        resp.setNomeEnfermeiroResponsavel(ubs.getNomeEnfermeiroResponsavel());

        return resp;
    }
}