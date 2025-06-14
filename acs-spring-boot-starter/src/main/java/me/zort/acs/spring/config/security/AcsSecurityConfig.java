package me.zort.acs.spring.config.security;

import me.zort.acs.core.domain.SubjectByTypeAndIdProvider;
import me.zort.acs.core.domain.SubjectProvider;
import me.zort.acs.client.v1.AcsClientV1;
import me.zort.acs.spring.*;
import me.zort.acs.spring.config.model.SubjectConfig;
import org.jetbrains.annotations.NotNull;
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
    @ConditionalOnMissingBean
    public AcsUserDetailsService userDetailsService(
            AcsClientV1 client,
            AcsSecuritySystemMatcherConfig systemMatcherConfig,
            AcsSecurityUserConfig userConfig, AcsSecurityConfigurer securityConfigurer) {
        return securityConfigurer.userDetailsService(
                client,
                pickSubjectProviderForConfig(systemMatcherConfig), userConfig.getSubjectType());
    }

    private static @NotNull SubjectProvider pickSubjectProviderForConfig(SubjectConfig config) {
        if (config.getSubjectType() != null && config.getSubjectId() != null) {
            return new SubjectByTypeAndIdProvider(config.getSubjectType(), config.getSubjectId());
        } else {
            throw new InvalidAcsConfigurationException("Invalid subject config! You probably forgot " +
                    "to set the right combination of properties.");
        }
    }
}
