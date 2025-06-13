package me.zort.acs.spring.config.security;

import me.zort.acs.client.v1.AcsClientV1;
import me.zort.acs.core.domain.SubjectProvider;
import me.zort.acs.spring.AcsUserDetailsService;

/**
 * Interface for configuring ACS security components within a Spring Boot application.
 * Provides a method to create a custom {@link AcsUserDetailsService} using the provided ACS client and subject provider.
 */
public interface AcsSecurityConfigurer {

    /**
     * Creates an instance of {@link AcsUserDetailsService} using the given ACS client, system subject provider, and user subject type.
     *
     * @param client the ACS client instance
     * @param systemSubjectProvider the provider for system subjects
     * @param userSubjectType the type of user subject
     * @return a configured {@link AcsUserDetailsService} instance
     */
    AcsUserDetailsService userDetailsService(
            AcsClientV1 client, SubjectProvider systemSubjectProvider, String userSubjectType);
}
