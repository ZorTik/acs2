package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.DynamicGroupEntity;
import me.zort.acs.data.id.DynamicGroupId;
import me.zort.acs.data.id.SubjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.util.List;

@NoRepositoryBean
public interface DynamicGroupRepository extends CrudRepository<DynamicGroupEntity, DynamicGroupId> {

    List<DynamicGroupEntity> findAllBySubject_Id(@NonNull SubjectId id);
}
