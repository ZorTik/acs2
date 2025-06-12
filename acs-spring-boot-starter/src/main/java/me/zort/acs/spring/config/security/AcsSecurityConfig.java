package me.zort.acs.spring.config.security;

import me.zort.acs.client.v1.AcsClientV1;
import me.zort.acs.spring.AcsUserDetailsProvider;
import me.zort.acs.spring.AcsUserDetailsService;
import me.zort.acs.spring.DelegatingAcsUserDetailsService;
import me.zort.acs.spring.SubjectProvider;
import me.zort.acs.spring.config.client.AcsClientAutoConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AcsClientAutoConfig.class)
@EnableConfigurationProperties({AcsSecuritySystemMatcherConfig.class, AcsSecurityUserConfig.class})
public class AcsSecurityConfig {

    @Bean
    @ConditionalOnBean(AcsUserDetailsProvider.class)
    @ConditionalOnMissingBean
    public AcsUserDetailsService userDetailsService(
            AcsClientV1 client,
            AcsSecuritySystemMatcherConfig systemMatcherConfig,
            AcsSecurityUserConfig userConfig, AcsUserDetailsProvider userDetailsProvider) {
        SubjectProvider systemSubjectProvider = SubjectProvider.pickForConfig(systemMatcherConfig);

        return new DelegatingAcsUserDetailsService(
                client, systemSubjectProvider.getSubject(client), userConfig.getSubjectType(), userDetailsProvider);
    }
}
