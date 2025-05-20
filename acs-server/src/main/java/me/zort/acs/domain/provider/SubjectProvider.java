package me.zort.acs.domain.provider;

import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SubjectProvider {

    @Cacheable(value = "subjects", key = "#type.id + ':' + #id")
    public Subject getSubject(SubjectType type, String id) {
        return new Subject(type, id);
    }
}
