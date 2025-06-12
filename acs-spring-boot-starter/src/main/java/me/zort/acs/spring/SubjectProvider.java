package me.zort.acs.spring;

import me.zort.acs.client.http.model.Subject;
import me.zort.acs.client.v1.AcsClientV1;
import me.zort.acs.spring.config.model.SubjectConfig;
import org.jetbrains.annotations.NotNull;

public interface SubjectProvider {

    Subject getSubject(AcsClientV1 client);

    static @NotNull SubjectProvider pickForConfig(SubjectConfig config) {
        if (config.getSubjectType() != null && config.getSubjectId() != null) {
            return new SubjectByTypeAndIdProvider(config.getSubjectType(), config.getSubjectId());
        } else {
            throw new InvalidAcsConfigurationException("Invalid subject config! You probably forgot " +
                    "to set the right combination of properties.");
        }
    }
}
