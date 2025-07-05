package me.zort.acs.spring;

import me.zort.acs.core.domain.SubjectByTypeAndIdProvider;
import me.zort.acs.core.domain.SubjectProvider;
import me.zort.acs.spring.config.model.SubjectConfig;
import org.jetbrains.annotations.NotNull;

public class DefaultSystemSubjectProviderFactory implements SystemSubjectProviderFactory {

    @Override
    public @NotNull SubjectProvider createSystemSubjectProvider(SubjectConfig config) {
        if (config.getSubjectType() != null && config.getSubjectId() != null) {
            return new SubjectByTypeAndIdProvider(config.getSubjectType(), config.getSubjectId());
        } else {
            throw new InvalidAcsConfigurationException("Invalid subject config! You probably forgot " +
                    "to set the right combination of properties.");
        }
    }
}
