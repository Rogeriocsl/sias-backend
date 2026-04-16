package br.com.sias.api.controller;

import br.com.sias.api.dto.EncaminhamentoRequest;
import br.com.sias.api.dto.EncaminhamentoResponse;
import br.com.sias.api.service.EncaminhamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
