package com.tools.rental.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI wildfitOpenAPI() {
        final var info = new Info();
        info.setTitle("Tool Rental API");
        info.setVersion("0.1.4");
        info.setDescription("REST API for Tool Rental application");
        return new OpenAPI().info(info);
    }

}

