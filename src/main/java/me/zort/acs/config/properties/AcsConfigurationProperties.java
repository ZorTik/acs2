package me.zort.acs.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "acs")
@Component
@Getter
public class AcsConfigurationProperties {
    private String delimiter;

}
