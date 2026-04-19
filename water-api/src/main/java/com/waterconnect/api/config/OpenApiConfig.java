package com.waterconnect.api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

/**
 * OpenAPI 3 metadata for Springdoc (Swagger UI and /v3/api-docs).
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI waterConnectOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Water Connect Management API")
                        .version("1.0.0")
                        .description(
                                "API for managing water connections, pipes, connectors, and work orders."));
    }
}
