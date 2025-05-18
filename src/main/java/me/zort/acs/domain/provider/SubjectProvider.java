package me.zort.acs.domain.provider;

import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.stereotype.Component;

@Component
public class SubjectProvider {

    public Subject getSubject(SubjectType type, String id) {
        return new Subject(type, id);
    }
}
