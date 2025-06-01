package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GroupId;
import me.zort.acs.data.id.SubjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface GrantRepository extends CrudRepository<GrantEntity, UUID> {

    List<GrantEntity> findByAccessor_IdAndAccessed_Id(SubjectId accessorId, SubjectId accessedId);

    Optional<GrantEntity> findByAccessor_IdAndAccessed_IdAndNode_Value(SubjectId accessorId, SubjectId accessedId, String value);

    Optional<GrantEntity> findByAccessor_IdAndAccessed_IdAndGroup_Id(SubjectId accessorId, SubjectId accessedId, GroupId groupId);

    int countByAccessor_Id(SubjectId accessorId);
}
