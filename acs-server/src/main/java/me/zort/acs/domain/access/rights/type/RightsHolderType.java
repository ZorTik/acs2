package me.zort.acs.domain.access.rights.type;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.GrantOptions;

import java.util.Optional;

public interface RightsHolderType<T extends RightsHolder> {

    Grant createGrantFromHolder(T holder, GrantOptions options);

    Optional<GrantEntity> findGrantEntityForHolder(T holder, SubjectId accessorId, SubjectId accessedId);

    boolean isPresentInSubjectType(T holder, SubjectType subjectType);

    Class<T> getHolderType();
}
