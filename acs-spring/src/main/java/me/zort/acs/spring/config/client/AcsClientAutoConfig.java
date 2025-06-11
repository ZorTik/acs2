package me.zort.acs.spring.config.client;

import me.zort.acs.client.AcsClient;
import me.zort.acs.client.v1.AcsClientV1;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AcsClientConfig.class)
public class AcsClientAutoConfig {
    // Auto config

    @Bean
    @ConditionalOnMissingBean
    public AcsClientV1 acsClient(AcsClientConfig clientConfig) {
        return AcsClient.v1()
                .withBaseUrl(clientConfig.getBaseUrl())
                // TODO: Adapter
                // TODO: Jackson serializer (spring uses jackson)
                .build();
    }
}
