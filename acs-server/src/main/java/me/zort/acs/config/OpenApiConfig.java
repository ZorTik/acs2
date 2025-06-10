package me.zort.acs.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(
        title = "ACS2",
        summary = "Access control management microservice",
        description = "A microservice for management of access rights between different " +
                "subjects and subject types."))
@Configuration
public class OpenApiConfig {
    // Definitions config
}
