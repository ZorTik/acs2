package me.zort.acs.spring.config.client;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("acs.client")
@Getter
public class AcsClientConfig {
    private String baseUrl;

}
