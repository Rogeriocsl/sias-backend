package br.com.sias.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SIAS API - Sistema Integrado Academia da Saúde")
                        .version("1.0")
                        .description("Documentação dos endpoints do sistema SIAS para integração entre UBSF e Academia da Saúde.")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento SIAS")
                                .email("suporte@sias.com.br")));
    }
}
