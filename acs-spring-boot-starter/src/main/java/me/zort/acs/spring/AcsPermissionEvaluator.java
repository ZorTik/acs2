package me.zort.acs.spring;

import lombok.extern.slf4j.Slf4j;
import me.zort.acs.client.AcsSubjectResolvable;
import me.zort.acs.client.http.model.Node;
import me.zort.acs.client.v1.AcsClientV1;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class AcsPermissionEvaluator implements PermissionEvaluator {
    private final AcsClientV1 client;
    private final AcsSubjectDetailsResolver subjectDetailsResolver;

    public AcsPermissionEvaluator(AcsClientV1 client, AcsSubjectDetailsResolver subjectDetailsResolver) {
        this.client =
                Objects.requireNonNull(client,  "client cannot be null");
        this.subjectDetailsResolver =
                Objects.requireNonNull(subjectDetailsResolver,  "subjectDetailsResolver cannot be null");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (!(permission instanceof String)) {
            log.error("Permission object is not of type String");
            return false;
        }

        AcsSubjectResolvable callerSubject = subjectDetailsResolver.resolveSubjectDetails(authentication.getDetails());
        AcsSubjectResolvable targetSubject = subjectDetailsResolver.resolveSubjectDetails(targetDomainObject);
        if (callerSubject == null) {
            // Caller is null, wtf??? This should be handled by AcsUserDetailsService
            log.warn("Authenticated user subject is null");
            return false;
        }
        if (targetSubject == null) {
            // Target is not supported subject
            log.warn("Unsupported target object type: {}",
                    targetDomainObject == null ? "null" : targetDomainObject.getClass().getName());
            return false;
        }

        return client.checkAccess(callerSubject, targetSubject, Set.of(Node.of((String) permission))).all();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        // We don't support unknown subjects
        return false;
    }
}
