package me.zort.acs.spring.system;

import me.zort.acs.client.v1.AcsClientV1;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(
        prefix = "acs.system", name = "enabled", havingValue = "true")
@Configuration
@EnableConfigurationProperties(AcsSystemConfig.class)
public class AcsSystemAutoConfig {
    // Auto config

    @Bean
    @ConditionalOnMissingBean
    public AcsUserDetailsService userDetailsService(
            AcsClientV1 client, AcsSystemConfig config, AcsUserDetailsProvider userDetailsProvider) {
        return new DelegatingAcsUserDetailsService(client, config, userDetailsProvider);
    }
}
