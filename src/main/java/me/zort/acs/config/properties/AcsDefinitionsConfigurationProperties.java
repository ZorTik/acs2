package me.zort.acs.config.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "acs.definitions")
@Component
public class AcsDefinitionsConfigurationProperties {
    private String source;

}
