package me.zort.acs.api.domain.mapper;

import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.data.id.GrantId;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Node;

public abstract class DomainGrantIdMapper implements DomainToPersistenceMapper<Grant, GrantId> {

    public abstract GrantId toPersistence(SubjectLike accessor, SubjectLike accessed, Node node);

    @Override
    public final GrantId toPersistence(Grant domain) {
        return toPersistence(domain.getAccessor(), domain.getAccessed(), domain.getNode());
    }
}
