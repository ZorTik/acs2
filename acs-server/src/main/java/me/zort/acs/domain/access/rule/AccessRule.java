package me.zort.acs.domain.access.rule;

import me.zort.acs.api.domain.access.AccessRequest;

public interface AccessRule {

    void onRequest(AccessRequest request);

    default boolean acceptsNullableSubjects() {
        return false;
    }
}
