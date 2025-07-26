package me.zort.acs.data.repository;

import me.zort.acs.api.data.repository.DynamicGroupRepository;
import me.zort.acs.data.entity.DynamicGroupEntity;
import me.zort.acs.data.id.DynamicGroupId;
import me.zort.acs.data.id.SubjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaDynamicGroupRepository extends JpaRepository<DynamicGroupEntity, DynamicGroupId>, DynamicGroupRepository {

    @Override
    List<DynamicGroupEntity> findAllBySubject_Id(@NotNull SubjectId id);
}
