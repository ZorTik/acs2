package me.zort.acs.api.domain.service;

import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;

import java.util.List;
import java.util.Optional;

public interface GrantService {

    Optional<Grant> addGrant(Subject accessor, Subject accessed, Node node);

    boolean removeGrant(Grant grant);

    boolean existsGrant(Subject accessor, Subject accessed, Node node);

    Optional<Grant> getGrant(Subject accessor, Subject accessed, Node node);

    List<Grant> getGrants(Subject accessor, Subject accessed);

    int getGrantsCount(Subject accessor);
}
