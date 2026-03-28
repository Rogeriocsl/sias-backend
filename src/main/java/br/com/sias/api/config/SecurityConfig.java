package br.com.sias.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF (necessário para APIs REST)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()    // Libera todos os endpoints da API
                        .requestMatchers("/swagger-ui/**").permitAll() // Libera o Swagger UI
                        .requestMatchers("/v3/api-docs/**").permitAll() // Libera a doc do OpenAPI
                        .anyRequest().authenticated() // Qualquer outra coisa exige login
                );

        return http.build();
    }
}