package me.zort.acs.data.repository;

import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.SubjectId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaGrantRepositoryCustom {

    List<GrantEntity> findAllBetween(SubjectId accessorId, SubjectId accessedId);

    List<GrantEntity> findAllBetween(SubjectId accessorId, String accessedTypeId);

    Optional<GrantEntity> findNodeGrant(SubjectId accessorId, SubjectId accessedId, String value);

    Optional<GrantEntity> findGroupGrant(SubjectId accessorId, SubjectId accessedId, UUID groupId);

    List<GrantEntity> findAllByGroupIn(SubjectId accessorId, String accessedTypeId, List<UUID> groupIds);

    int countByAccessorId(SubjectId accessorId);
}
