package com.rbalazs.securityapi.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc / OpenAPI Configuration class.
 *
 * The @SecurityScheme annotations allows to add "Authorization: Bearer" to the CURL´s examples generated via
 * API Documentation/Swagger.
 *
 * -In order to see the "Authorization: Bearer" into the CURLs examples make sure to =>
 * 1) http://<project_url>/swagger-ui/index.html > click over 'Authorize'
 * 2) http://<project_url>/swagger-ui/index.html > GET /products > Try it out > Execute
 *
 * @author Rodrigo Balazs
 */
@Configuration
@SecurityScheme(
        name = "Bearer_Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfiguration {}