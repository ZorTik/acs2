package me.zort.acs.domain.service;

import me.zort.acs.domain.model.AccessRequest;
import me.zort.acs.domain.rule.AccessRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessControlService {
    private final List<AccessRule> accessRules;

    public AccessControlService(List<AccessRule> accessRules) {
        this.accessRules = accessRules;
    }

    public void checkAccess(AccessRequest request) {
        boolean hasNullableSubjects = request.getAccessor().isNull() || request.getAccessed().isNull();

        for (AccessRule rule : accessRules) {
            if (hasNullableSubjects && !rule.acceptsNullableSubjects()) {
                continue;
            }

            rule.onRequest(request);

            if (request.isGranted()) {
                return;
            }
        }
    }
}
