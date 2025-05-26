package me.zort.acs.config;

import lombok.RequiredArgsConstructor;
import me.zort.acs.config.properties.AcsDefinitionsConfigurationProperties;
import me.zort.acs.api.domain.definitions.source.DefinitionsSource;
import me.zort.acs.domain.definitions.source.InputStreamDefinitionsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Configuration
public class DefinitionsConfig {
    private final AcsDefinitionsConfigurationProperties definitionsProperties;
    private final ResourceLoader resourceLoader;

    @Bean
    public DefinitionsSource definitionsSource() {
        Resource resource = resourceLoader.getResource(definitionsProperties.getSource());
        if (!resource.exists()) {
            throw new IllegalStateException("Definitions resource not found at: " + definitionsProperties.getSource());
        }

        return new InputStreamDefinitionsSource(resource, definitionsProperties.getFormat());
    }
}
