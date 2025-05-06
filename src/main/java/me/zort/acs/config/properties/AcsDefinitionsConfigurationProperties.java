package me.zort.acs.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@ConfigurationProperties(prefix = "acs.definitions")
@Component
public class AcsDefinitionsConfigurationProperties {
    private String source;

}
