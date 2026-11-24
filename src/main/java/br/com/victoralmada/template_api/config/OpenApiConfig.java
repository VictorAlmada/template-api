package br.com.victoralmada.template_api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Template API")
                        .description("Projeto base para APIs Spring Boot")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Victor Almada")
                                .email("victor_mathers@hotmail.com"))
                        .license(new License()
                                .name("MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Github")
                        .url("https://github.com/VictorAlmada"));

    }

}
