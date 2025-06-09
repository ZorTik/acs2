package me.zort.acs.api.domain.grant;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.SubjectId;

import java.util.Optional;

public interface GrantRepositoryAdapter {

    Optional<GrantEntity> getGrantEntity(SubjectId accessorId, SubjectId accessedId, RightsHolder rightsHolder);
}
