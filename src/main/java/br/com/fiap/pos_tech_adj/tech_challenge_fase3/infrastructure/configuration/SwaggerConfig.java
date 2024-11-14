package br.com.fiap.pos_tech_adj.tech_challenge_fase3.infrastructure.configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tech Challenge Fase 3 API")
                        .version("1.0")
                        .description("Documentação da API para o projeto Tech Challenge Fase 3")
                        .contact(new Contact()
                                .name("Seu Nome")
                                .email("seuemail@dominio.com")
                                .url("https://www.fiap.com.br")
                        ));
    }
}
