package me.zort.acs.config.properties;

import lombok.Data;
import me.zort.acs.domain.definitions.DefinitionsFormat;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "acs.definitions")
@Component
public class AcsDefinitionsConfigurationProperties {
    private String source;
    private DefinitionsFormat format;

}
