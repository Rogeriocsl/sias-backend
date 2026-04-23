package br.com.sias.api.controller;

import br.com.sias.api.dto.EnderecoRequest;
import br.com.sias.api.dto.EnderecoResponse;
import br.com.sias.api.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")

public class EnderecoController {

    private final EnderecoService service;

    public EnderecoController(EnderecoService service) {
        this.service = service;
    }

    @GetMapping
    public List<EnderecoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public EnderecoResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public EnderecoResponse criar(@RequestBody @Valid EnderecoRequest req) {
        return service.criar(req);
    }

    @PutMapping("/{id}")
    public EnderecoResponse atualizar(@PathVariable Long id, @RequestBody @Valid EnderecoRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}