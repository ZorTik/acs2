package me.zort.acs.domain.provider;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.model.*;
import me.zort.acs.domain.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelProvider {

    public Grant getGrant(Subject accessor, Subject accessed, Node node) {
        return new Grant(accessor, accessed, node);
    }

    public Subject getSubject(SubjectType type, String id) {
        return new Subject(type, id);
    }
}
