package me.zort.acs.data.repository;

import me.zort.acs.api.data.repository.GrantRepository;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GroupId;
import me.zort.acs.data.id.SubjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaGrantRepository extends JpaRepository<GrantEntity, UUID>, GrantRepository {

    @Override
    List<GrantEntity> findByAccessor_IdAndAccessed_Id(SubjectId accessorId, SubjectId accessedId);

    @Override
    Optional<GrantEntity> findByAccessor_IdAndAccessed_IdAndNode_Value(SubjectId accessorId, SubjectId accessedId, String value);

    @Override
    Optional<GrantEntity> findByAccessor_IdAndAccessed_IdAndGroup_Id(SubjectId accessorId, SubjectId accessedId, GroupId groupId);

    @Override
    int countByAccessor_Id(SubjectId accessorId);
}
