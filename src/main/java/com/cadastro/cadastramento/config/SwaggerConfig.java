package com.cadastro.cadastramento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI custormOpenAPI(){
        return new OpenAPI()
        .info(new Info()
        .title("Api de Cadastro de Pasteis")
        .version("v1")
        .description("Api de Cadastro de Pasteis")
        .termsOfService(null)
        .license(null));
    }
    
}
