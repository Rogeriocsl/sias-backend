package br.com.sias.api.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final String ISSUER = "sias-api";

    public String gerarToken(String login) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(login)
                .withExpiresAt(expiracaoToken())
                .sign(Algorithm.HMAC256(secret));
    }

    public String validarToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject(); // retorna o login se válido
        } catch (JWTVerificationException e) {
            return null; // token inválido ou expirado
        }
    }

    private Instant expiracaoToken() {
        return LocalDateTime.now()
                .plusHours(8)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
