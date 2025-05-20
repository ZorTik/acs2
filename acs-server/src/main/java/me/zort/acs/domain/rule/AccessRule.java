package me.zort.acs.domain.rule;

import me.zort.acs.domain.model.AccessRequest;

public interface AccessRule {

    void onRequest(AccessRequest request);

    default boolean acceptsNullableSubjects() {
        return false;
    }
}
