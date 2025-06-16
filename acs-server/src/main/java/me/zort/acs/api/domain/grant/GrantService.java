package me.zort.acs.api.domain.grant;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.grant.exception.GrantAlreadyExistsException;
import me.zort.acs.domain.grant.exception.InvalidGrantException;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
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

    /**
     * Returns ALL nodes that are assignable to accessed's subject type and their
     * grant states in relation with accessor.
     *
     * @param accessor The accessor subject
     * @param accessed The accessed subject
     * @return The nodes and their states
     */
    Map<Node, Boolean> getGrantStatesFor(SubjectLike accessor, SubjectLike accessed);
}
