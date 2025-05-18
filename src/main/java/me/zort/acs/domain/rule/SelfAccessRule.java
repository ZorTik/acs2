package me.zort.acs.domain.rule;

import me.zort.acs.domain.model.AccessRequest;
import org.springframework.stereotype.Component;

@Component
public class SelfAccessRule implements AccessRule {
    @Override
    public void onRequest(AccessRequest request) {
        if (request.getAccessor().equals(request.getAccessed())) {
            request.grant();
        }
    }
}
