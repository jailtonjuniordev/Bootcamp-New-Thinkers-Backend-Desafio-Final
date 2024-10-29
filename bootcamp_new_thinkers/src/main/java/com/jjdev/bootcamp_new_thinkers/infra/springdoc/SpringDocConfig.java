package com.jjdev.bootcamp_new_thinkers.infra.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Desafio Final Bootcamp New Thinkers")
                        .contact(new Contact()
                                .name("Jailton Júnior - Fullstack Developer")
                                .email("contato.jailtonjr.dev@gmail.com")
                                .url("https://github.com/jailtonjuniordev"))
                        .description("API desenvolvida para como trabalho de conclusão do desafio final do Bootcamp New Thinkers da Squadra, ministrado por Wanderlucio!")
                        .license(new License()
                                .name("MIT Licence Modified")
                                .url("https://github.com/jailtonjuniordev/Bootcamp-New-Thinkers-Backend-Desafio-Final/blob/main/LICENCE.md")));

    }
}
