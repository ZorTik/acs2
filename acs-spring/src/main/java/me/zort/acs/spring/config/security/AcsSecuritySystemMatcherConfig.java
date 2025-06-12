package me.zort.acs.spring.config.security;

import lombok.Getter;
import me.zort.acs.spring.config.model.SubjectConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("acs.security.system.matcher")
public class AcsSecuritySystemMatcherConfig implements SubjectConfig {
    private String subjectType;
    private Object subjectId;

    // TODO: More options to match system subject
}
