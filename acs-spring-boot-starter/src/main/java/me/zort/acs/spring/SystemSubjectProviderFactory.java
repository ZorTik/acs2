package me.zort.acs.spring;

import me.zort.acs.core.domain.SubjectProvider;
import me.zort.acs.spring.config.model.SubjectConfig;
import org.jetbrains.annotations.NotNull;

/**
 * Factory interface for creating system {@link SubjectProvider} instances
 * based on the provided {@link SubjectConfig}.
 * <p>
 * Implementations of this interface are responsible for instantiating
 * subject providers that represent system-level subjects according to
 * the given configuration.
 * </p>
 */
public interface SystemSubjectProviderFactory {

    /**
     * Creates a system {@link SubjectProvider} based on the provided configuration.
     *
     * @param config the configuration for the subject provider
     * @return a new instance of {@link SubjectProvider}
     * @throws InvalidAcsConfigurationException if the configuration is invalid
     */
    @NotNull
    SubjectProvider createSystemSubjectProvider(SubjectConfig config) throws InvalidAcsConfigurationException;
}
