package me.zort.acs.domain.provider;

import me.zort.acs.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class ModelProvider {

    public AccessRequest getAccessRequest(SubjectLike from, SubjectLike to, Node node) {
        return new AccessRequest(from, to, node);
    }

    public Grant getGrant(Subject accessor, Subject accessed, Node node) {
        return new Grant(accessor, accessed, node);
    }

    public Subject getSubject(SubjectType type, String id) {
        return new Subject(type, id);
    }
}
