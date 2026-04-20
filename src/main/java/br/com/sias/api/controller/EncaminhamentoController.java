package br.com.sias.api.controller;

import br.com.sias.api.dto.EncaminhamentoRequest;
import br.com.sias.api.dto.EncaminhamentoResponse;
import br.com.sias.api.model.Encaminhamento;
import br.com.sias.api.model.enums.EncaminhamentoStatus;
import br.com.sias.api.service.EncaminhamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encaminhamento")
@CrossOrigin(origins = "*")
public class EncaminhamentoController {

    @Autowired
    private EncaminhamentoService service;

    @PostMapping
    public ResponseEntity<EncaminhamentoResponse> criar(@RequestBody EncaminhamentoRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping
    public List<Encaminhamento> listar(){
        return service.listar();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EncaminhamentoResponse> atualizar(@PathVariable Long id, @RequestBody EncaminhamentoRequest dto){
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/encaminhamento/{pacienteId}")
    public List<EncaminhamentoResponse> BuscarEncaminhamento(@PathVariable Long pacienteId){
        return service.buscarEncaminhamento(pacienteId);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EncaminhamentoResponse>>listarPorStatus(@PathVariable EncaminhamentoStatus status){
        return ResponseEntity.ok(service.listarEncaminhamentos(status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();

    }
}
