package me.zort.acs.domain.provider;

import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import org.springframework.stereotype.Component;

@Component
public class ModelProvider {

    public Grant getGrant(Subject accessor, Subject accessed, Node node) {
        return new Grant(accessor, accessed, node);
    }
}
