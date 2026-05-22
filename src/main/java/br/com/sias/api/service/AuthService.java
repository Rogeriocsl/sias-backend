package br.com.sias.api.service;

import br.com.sias.api.dto.LoginRequest;
import br.com.sias.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String autenticar(LoginRequest request) {
        UsernamePasswordAuthenticationToken credenciais =
                new UsernamePasswordAuthenticationToken(request.login(), request.senha());

        Authentication auth = authenticationManager.authenticate(credenciais);

        return jwtService.gerarToken(auth.getName());
    }
}