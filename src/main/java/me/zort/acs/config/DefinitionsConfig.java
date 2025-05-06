package me.zort.acs.config;

import lombok.RequiredArgsConstructor;
import me.zort.acs.config.properties.AcsDefinitionsConfigurationProperties;
import me.zort.acs.domain.definitions.DefinitionsSource;
import me.zort.acs.domain.definitions.yaml.YamlDefinitionsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.ResourceLoader;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Configuration
public class DefinitionsConfig {
    private final AcsDefinitionsConfigurationProperties definitionsConfigurationProperties;
    private final ResourceLoader resourceLoader;

    @Bean
    public DefinitionsSource definitionsSource() {
        InputStreamSource inputStreamSource = resourceLoader.getResource(
                definitionsConfigurationProperties.getSource());

        return new YamlDefinitionsSource(inputStreamSource);
    }
}
