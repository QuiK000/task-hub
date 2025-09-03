package com.dev.quikkkk.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Task Hub REST API",
                        email = "kexitttttt@gmail.com",
                        url = "https://github.com/QuiK000/task-hub"
                ),
                description = "OpenAPI Documentation for Task Hub REST API",
                title = "OpenAPI Specification",
                version = "1.0",
                license = @License(
                        name = "...",
                        url = "https://github.com/QuiK000/task-hub/license"
                ),
                termsOfService = "https://github.com/QuiK000/task-hub/terms"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Development Server | Local ENV"
                ),
                @Server(
                        description = "Production Server | Docker ENV",
                        url = "https://task-hub.herokuapp.com"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
