package me.zort.acs.api.domain.access;

import me.zort.acs.domain.model.Node;
import me.zort.acs.api.domain.model.SubjectLike;

public interface AccessRequest {

    SubjectLike getAccessor();

    SubjectLike getAccessed();

    Node getNode();

    void grant();

    boolean isGranted();
}
