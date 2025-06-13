package me.zort.acs.config;

import lombok.RequiredArgsConstructor;
import me.zort.acs.config.properties.AcsDefinitionsConfigurationProperties;
import me.zort.acs.core.domain.definitions.source.DefinitionsSource;
import me.zort.acs.core.domain.definitions.source.InputStreamDefinitionsSource;
import me.zort.acs.core.domain.definitions.validation.DefinitionsValidator;
import me.zort.acs.core.domain.definitions.validation.DefinitionsValidators;
import me.zort.acs.core.domain.definitions.validation.ValidationContext;
import me.zort.acs.core.domain.definitions.validation.visitor.DefinitionsVisitor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Configuration
public class DefinitionsConfig {
    private final AcsDefinitionsConfigurationProperties definitionsProperties;
    private final ResourceLoader resourceLoader;

    @Bean
    public DefinitionsSource definitionsSource() throws MalformedURLException {
        String source = definitionsProperties.getSource();

        Resource resource;
        if (Arrays.stream(new String[] {"http://", "https://"})
                .anyMatch(source::startsWith)) {
            resource = new UrlResource(source);
        } else {
            resource = resourceLoader.getResource(source);
        }

        if (!resource.exists()) {
            throw new IllegalStateException("Definitions resource not found at: " + source);
        }

        return new InputStreamDefinitionsSource(resource, definitionsProperties.getFormat());
    }

    @Bean
    public ObjectFactory<ValidationContext> validationContextFactory() {
        return ValidationContext::new;
    }

    @Bean
    public DefinitionsValidator definitionsValidator(
            ObjectFactory<ValidationContext> validationContextFactory, List<DefinitionsVisitor> visitors) {
        return DefinitionsValidators.simple(validationContextFactory, visitors);
    }
}
