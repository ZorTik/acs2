package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GrantId;
import me.zort.acs.data.id.SubjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GrantRepository extends CrudRepository<GrantEntity, GrantId> {

    List<GrantEntity> findByAccessor_IdAndAccessed_Id(SubjectId accessorId, SubjectId accessedId);

    int countByAccessor_Id(SubjectId accessorId);
}
