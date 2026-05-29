package com.antonioyassine.gameapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configuración de Swagger UI: define la info de la API y el botón "Authorize" con JWT
@Configuration
public class OpenApiConfig {

    // Nombre del esquema de seguridad que aparecerá en el botón Authorize
    private static final String SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI gameApiOpenAPI() {
        return new OpenAPI()
                // Información general que se muestra en la cabecera de Swagger UI
                .info(new Info()
                        .title("GameAPI - API REST de Videojuegos")
                        .version("1.0")
                        .description("API para gestionar videojuegos, estudios y géneros. "
                                + "Los GET son públicos; POST, PUT y DELETE requieren token JWT."))
                // Aplica el requisito de seguridad globalmente (el candado en cada endpoint)
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                // Define el esquema: token Bearer JWT introducido desde el botón Authorize
                .components(new Components().addSecuritySchemes(SCHEME_NAME,
                        new SecurityScheme()
                                .name(SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
