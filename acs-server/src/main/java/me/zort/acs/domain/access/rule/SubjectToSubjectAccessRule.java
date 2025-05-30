package me.zort.acs.domain.access.rule;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.request.AccessRequest;
import me.zort.acs.api.domain.access.request.SubjectToSubjectAccessRequest;

@RequiredArgsConstructor
public abstract class SubjectToSubjectAccessRule implements AccessRule {
    private final boolean acceptsNullableSubjects;

    public SubjectToSubjectAccessRule() {
        this(false);
    }

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
