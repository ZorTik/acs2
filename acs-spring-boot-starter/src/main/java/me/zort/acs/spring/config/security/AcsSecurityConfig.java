package me.zort.acs.spring.config.security;

import me.zort.acs.core.domain.SubjectProvider;
import me.zort.acs.client.v1.AcsClientV1;
import me.zort.acs.spring.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AcsSecuritySystemMatcherConfig.class, AcsSecurityUserConfig.class})
public class AcsSecurityConfig {

    @Bean
    @ConditionalOnBean(AcsSecurityConfigurer.class)
    @ConditionalOnMissingBean(AcsUserDetailsService.class)
    public AcsUserDetailsService userDetailsService(
            AcsClientV1 client,
            AcsSecuritySystemMatcherConfig systemMatcherConfig,
            AcsSecurityUserConfig userConfig,
            AcsSecurityConfigurer securityConfigurer,
            @Autowired(required = false) SystemSubjectProviderFactory systemSubjectProviderFactory) {
        if (systemSubjectProviderFactory == null) {
            systemSubjectProviderFactory = new DefaultSystemSubjectProviderFactory();
        }

        SubjectProvider systemSubjectProvider = systemSubjectProviderFactory
                .createSystemSubjectProvider(systemMatcherConfig);
        return securityConfigurer.userDetailsService(client, systemSubjectProvider, userConfig.getSubjectType());
    }
}
