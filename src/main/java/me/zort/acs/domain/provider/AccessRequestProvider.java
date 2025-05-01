package me.zort.acs.domain.provider;

import me.zort.acs.domain.AccessRequest;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import org.springframework.stereotype.Component;

@Component
public class AccessRequestProvider {

    public AccessRequest getAccessRequest(Subject from, Subject to, Node node) {
        return new AccessRequest(from, to, node);
    }
}
