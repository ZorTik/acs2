package me.zort.acs.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "acs")
@Component
public class AcsConfigurationProperties {
    private String delimiter;

}
