package br.com.sias.api.service;

import br.com.sias.api.dto.EnderecoRequest;
import br.com.sias.api.dto.EnderecoResponse;
import br.com.sias.api.model.Endereco;
import br.com.sias.api.repository.EnderecoRepository;
import br.com.sias.api.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository) {
        this.repository = repository;
    }

    public List<EnderecoResponse> listar() {
        return repository.findAll().stream().map(e ->
                new EnderecoResponse(
                        e.getId(),
                        e.getRua(),
                        e.getNumero(),
                        e.getBairro(),
                        e.getCidade(),
                        e.getEstado(),
                        e.getCep(),
                        e.getComplemento()
                )
        ).toList();
    }

    public EnderecoResponse buscarPorId(Long id) {
        Endereco e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));

        return new EnderecoResponse(
                e.getId(),
                e.getRua(),
                e.getNumero(),
                e.getBairro(),
                e.getCidade(),
                e.getEstado(),
                e.getCep(),
                e.getComplemento()
        );
    }

    public EnderecoResponse criar(EnderecoRequest req) {
        Endereco e = new Endereco();

        e.setRua(req.rua());
        e.setNumero(req.numero());
        e.setBairro(req.bairro());
        e.setCidade(req.cidade());
        e.setEstado(req.estado());
        e.setCep(req.cep());
        e.setComplemento(req.complemento());

        return toResponse(repository.save(e));
    }

    public EnderecoResponse atualizar(Long id, EnderecoRequest req) {
        Endereco e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));

        e.setRua(req.rua());
        e.setNumero(req.numero());
        e.setBairro(req.bairro());
        e.setCidade(req.cidade());
        e.setEstado(req.estado());
        e.setCep(req.cep());
        e.setComplemento(req.complemento());

        return toResponse(repository.save(e));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Endereço não encontrado");
        }
        repository.deleteById(id);
    }

    private EnderecoResponse toResponse(Endereco e) {
        return new EnderecoResponse(
                e.getId(),
                e.getRua(),
                e.getNumero(),
                e.getBairro(),
                e.getCidade(),
                e.getEstado(),
                e.getCep(),
                e.getComplemento()
        );
    }
}