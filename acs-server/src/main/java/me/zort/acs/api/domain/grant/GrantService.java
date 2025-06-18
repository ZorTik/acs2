package me.zort.acs.api.domain.grant;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.domain.grant.exception.GrantAlreadyExistsException;
import me.zort.acs.domain.grant.exception.InvalidGrantException;
import me.zort.acs.domain.model.Subject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface GrantService {

    @NotNull
    Grant addGrant(
            Subject accessor,
            Subject accessed, RightsHolder rightsHolder) throws GrantAlreadyExistsException, InvalidGrantException;

    boolean removeGrant(Grant grant);

    Optional<Grant> getGrant(Subject accessor, Subject accessed, RightsHolder rightsHolder);

    List<Grant> getGrants(Subject accessor, Subject accessed);

    int getGrantsCount(Subject accessor);
}
