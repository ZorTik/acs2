package me.zort.acs.data.repository;

import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GrantId;
import me.zort.acs.data.id.SubjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrantRepository extends JpaRepository<GrantEntity, GrantId> {

    List<GrantEntity> findByAccessor_IdAndAccessed_Id(SubjectId accessorId, SubjectId accessedId);

    int countByAccessor_Id(SubjectId accessorId);
}
