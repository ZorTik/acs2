package me.zort.acs.domain.access.rule;

import me.zort.acs.api.domain.access.AccessRequest;
import org.springframework.stereotype.Component;

@Component
public class SelfAccessRule implements AccessRule {
    @Override
    public void onRequest(AccessRequest request) {
        if (request.getAccessor().equals(request.getAccessed())) {
            request.grant();
        }
    }

    @Override
    public boolean acceptsNullableSubjects() {
        return true;
    }
}
