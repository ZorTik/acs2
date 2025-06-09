package me.zort.acs.spring.system;

import lombok.Getter;
import me.zort.acs.client.http.model.Subject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@ConfigurationProperties("acs.system")
public class AcsSystemConfig {
    @Getter
    private boolean enabled;
    private String systemSubjectType = null;
    private String systemSubjectId = null;
    @Getter
    private String userSubjectType = null;

    public void setSystemSubject(Subject subject) {
        this.systemSubjectType = subject.getGroup();
        this.systemSubjectId = (String) subject.getId();
    }

    public void setUserSubjectType(String userSubjectType) {
        this.userSubjectType = Objects.requireNonNull(userSubjectType, "User subject type cannot be null");
    }

    public Subject getSystemSubject() {
        return Subject.of(systemSubjectType, systemSubjectId);
    }
}
