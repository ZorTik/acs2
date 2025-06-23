package me.zort.acs.spring.config.security;

import me.zort.acs.core.domain.SubjectProvider;
import me.zort.acs.client.v1.AcsClientV1;
import me.zort.acs.spring.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;

@Configuration
@EnableConfigurationProperties({AcsSecuritySystemMatcherConfig.class, AcsSecurityUserConfig.class})
public class AcsSecurityConfig {

    @Bean
    @ConditionalOnMissingBean(SystemSubjectProviderFactory.class)
    public SystemSubjectProviderFactory systemSubjectProviderFactory() {
        return new DefaultSystemSubjectProviderFactory();
    }

    @Bean
    @ConditionalOnMissingBean(AcsSubjectDetailsResolver.class)
    public AcsSubjectDetailsResolver subjectDetailsResolver() {
        return new DirectSubjectDetailsResolver();
    }

    @Bean
    @ConditionalOnBean(AcsSecurityConfigurer.class)
    @ConditionalOnMissingBean(AcsUserDetailsService.class)
    public AcsUserDetailsService userDetailsService(
            AcsClientV1 client,
            AcsSecurityUserConfig userConfig,
            AcsSecurityConfigurer securityConfigurer,
            SystemSubjectProviderFactory systemSubjectProviderFactory,
            AcsSecuritySystemMatcherConfig systemMatcherConfig) {
        SubjectProvider systemSubjectProvider = systemSubjectProviderFactory
                .createSystemSubjectProvider(systemMatcherConfig);

        return securityConfigurer.userDetailsService(client, systemSubjectProvider, userConfig.getSubjectType());
    }

    @Bean
    @ConditionalOnBean(AcsClientV1.class)
    public PermissionEvaluator permissionEvaluator(
            AcsClientV1 client, AcsSubjectDetailsResolver subjectDetailsResolver) {
        return new AcsPermissionEvaluator(client, subjectDetailsResolver);
    }

    @Bean
    @ConditionalOnMissingBean(MethodSecurityExpressionHandler.class)
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(PermissionEvaluator permissionEvaluator) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);

        return expressionHandler;
    }
}
