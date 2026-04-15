package com.moviereview.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure les métadonnées exposées par OpenAPI pour l'API.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Crée la définition OpenAPI utilisée par Swagger UI et les clients générés.
     *
     * @return la configuration OpenAPI de l'application.
     */
    @Bean
    public OpenAPI movieReviewOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie Review API")
                        .version("v1")
                        .description("API REST pour gérer les films, les users et les reviews"));
    }
}


