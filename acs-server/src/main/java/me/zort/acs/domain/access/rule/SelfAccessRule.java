package me.zort.acs.domain.access.rule;

import me.zort.acs.api.domain.access.request.SubjectToSubjectAccessRequest;
import org.springframework.stereotype.Component;

@Component
public class SelfAccessRule extends SubjectToSubjectAccessRule {

    public SelfAccessRule() {
        super(true);
    }

    @Override
    public void onRequest(SubjectToSubjectAccessRequest request) {
        if (request.getAccessor().equals(request.getAccessed())) {
            request.grant();
        }
    }
}
