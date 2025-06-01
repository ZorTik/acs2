package me.zort.acs.api.domain.service;

import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.domain.model.Subject;

import java.util.List;
import java.util.Optional;

public interface GrantService {

    Optional<Grant> addGrant(Subject accessor, Subject accessed, RightsHolder rightsHolder);

    boolean removeGrant(Grant grant);

    boolean existsGrant(Subject accessor, Subject accessed, RightsHolder rightsHolder);

    Optional<Grant> getGrant(Subject accessor, Subject accessed, RightsHolder rightsHolder);

    List<Grant> getGrants(Subject accessor, Subject accessed);

    int getGrantsCount(Subject accessor);
}
