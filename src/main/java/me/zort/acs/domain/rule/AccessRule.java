package me.zort.acs.domain.rule;

import me.zort.acs.domain.AccessRequest;

public interface AccessRule {

    void onRequest(AccessRequest request);
}
