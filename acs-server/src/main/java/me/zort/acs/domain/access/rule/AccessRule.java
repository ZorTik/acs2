package me.zort.acs.domain.access.rule;

import me.zort.acs.api.domain.access.request.AccessRequest;
import me.zort.acs.domain.access.AccessQueryable;

public interface AccessRule extends AccessQueryable {

    void onRequest(AccessRequest request);
}
