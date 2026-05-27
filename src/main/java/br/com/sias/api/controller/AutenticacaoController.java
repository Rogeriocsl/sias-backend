package br.com.sias.api.controller;

import br.com.sias.api.dto.LoginRequest;
import br.com.sias.api.dto.LoginResponse;
import br.com.sias.api.model.Usuario;
import br.com.sias.api.repository.UsuarioRepository;
import br.com.sias.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest data) {
        String token = authService.autenticar(data);

        Usuario usuario = usuarioRepository.findByLogin(data.login())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return ResponseEntity.ok(new LoginResponse(
                token,
                usuario.getLogin(),
                usuario.getPerfil(),
                usuario.getNome()
        ));
    }
}