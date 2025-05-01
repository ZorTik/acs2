package me.zort.acs.domain.service;

import me.zort.acs.domain.AccessRequest;
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
        accessRules.forEach(rule -> rule.onRequest(request));
    }
}
