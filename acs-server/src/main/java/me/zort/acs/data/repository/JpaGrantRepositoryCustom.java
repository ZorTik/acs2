package me.zort.acs.data.repository;

import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GroupId;
import me.zort.acs.data.id.SubjectId;

import java.util.List;
import java.util.Optional;

public interface JpaGrantRepositoryCustom {

    List<GrantEntity> findAllBetween(SubjectId accessorId, SubjectId accessedId);

    List<GrantEntity> findAllBetween(SubjectId accessorId, String accessedTypeId);

    Optional<GrantEntity> findNodeGrant(SubjectId accessorId, SubjectId accessedId, String value);

    Optional<GrantEntity> findGroupGrant(SubjectId accessorId, SubjectId accessedId, GroupId groupId);

    List<GrantEntity> findAllByGroupIn(SubjectId accessorId, String accessedTypeId, List<GroupId> groupIds);

    int countByAccessorId(SubjectId accessorId);
}
