package me.zort.acs.data.repository;

import me.zort.acs.api.data.repository.GrantRepository;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GrantId;
import me.zort.acs.data.id.SubjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaGrantRepository extends JpaRepository<GrantEntity, GrantId>, GrantRepository {

    @Override
    List<GrantEntity> findByAccessor_IdAndAccessed_Id(SubjectId accessorId, SubjectId accessedId);

    @Override
    int countByAccessor_Id(SubjectId accessorId);
}
