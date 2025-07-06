package me.zort.acs.spring.config.client;

import me.zort.acs.client.AcsClient;
import me.zort.acs.client.v1.AcsClientV1;
import me.zort.acs.spring.SpringAcsHttpSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;

@Configuration
@EnableConfigurationProperties(AcsClientConfig.class)
public class AcsClientAutoConfig {
    // Auto config

    @Bean
    @ConditionalOnMissingBean(AcsClientV1.class)
    public AcsClientV1 acsClient(AcsClientConfig clientConfig, AbstractJsonHttpMessageConverter messageConverter) {
        return AcsClient.v1()
                .withBaseUrl(clientConfig.getBaseUrl())
                // TODO: Adapter
                .withHttpSerializer(new SpringAcsHttpSerializer(messageConverter))
                .build();
    }
}
