package me.zort.acs.spring.config.security;

import me.zort.acs.client.v1.AcsClientV1;
import me.zort.acs.spring.AcsUserDetailsProvider;
import me.zort.acs.spring.AcsUserDetailsService;
import me.zort.acs.spring.DelegatingAcsUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AcsSecurityConfig.class)
public class AcsSecurityAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public AcsUserDetailsService userDetailsService(
            AcsClientV1 client, AcsUserDetailsProvider userDetailsProvider) {
        return new DelegatingAcsUserDetailsService(client, config, userDetailsProvider);
    }
}
