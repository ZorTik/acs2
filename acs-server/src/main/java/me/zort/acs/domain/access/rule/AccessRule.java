package me.zort.acs.domain.access.rule;

import me.zort.acs.api.domain.access.request.AccessRequest;

public interface AccessRule {

    void onRequest(AccessRequest request);

}
