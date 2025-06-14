package me.zort.acs_plane.config;

import me.zort.acs.core.domain.definitions.validation.DefinitionsValidator;
import me.zort.acs.core.domain.definitions.validation.DefinitionsValidators;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefinitionsConfig {

    @Bean
    public DefinitionsValidator definitionsValidator() {
        return DefinitionsValidators.simple();
    }
}
