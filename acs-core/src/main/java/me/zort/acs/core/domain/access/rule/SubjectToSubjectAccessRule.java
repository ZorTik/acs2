package me.zort.acs.core.domain.access.rule;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.access.request.AccessRequest;
import me.zort.acs.core.domain.access.request.SubjectToSubjectAccessRequest;

/**
 * An abstract access rule that processes subject-to-subject access requests.
 * It provides a mechanism to handle requests where one subject is trying to access another subject.
 * The rule can be configured to accept or reject requests involving nullable subjects.
 *
 * @author ZorTik
 */
@RequiredArgsConstructor
public abstract class SubjectToSubjectAccessRule implements AccessRule {
    private final boolean acceptsNullableSubjects;

    public abstract void onRequest(SubjectToSubjectAccessRequest request);

    @Override
    public void onRequest(AccessRequest request) {
        if (request instanceof SubjectToSubjectAccessRequest stsAccessRequest && acceptsRequest(stsAccessRequest)) {
            onRequest(stsAccessRequest);
        }
    }

    private boolean acceptsRequest(SubjectToSubjectAccessRequest request) {
        boolean hasNullableSubjects = request.getAccessor().isNull() || request.getAccessed().isNull();

        return !hasNullableSubjects || acceptsNullableSubjects;
    }
}
