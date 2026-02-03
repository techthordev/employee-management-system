package br.com.techthordev.employee_management_system.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI employeeManagementOpenAPI() {

        return new OpenAPI()

                // --- API Metadata ---
                .info(new Info()
                        .title("Employee Management API")
                        .description("""
                                Production-grade REST API for managing employees.

                                This API is built using Spring Boot following
                                enterprise best practices:
                                - explicit REST controllers
                                - layered architecture
                                - DTO-based API contracts
                                - validation and consistent error handling
                                """)
                        .version("v1")
                        .contact(new Contact()
                                .name("Thorsten Fey")
                                .url("https://techthordev.com.br")
                                .email("contact@techthordev.com.br"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT"))
                )

                // --- Global Security (JWT-ready) ---
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT Authorization header using the Bearer scheme"))
                )

                // Apply security globally (can be overridden per endpoint later)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
