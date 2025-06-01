package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.id.GroupId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GroupRepository extends CrudRepository<GroupEntity, GroupId> {

    List<GroupEntity> findAllBySubjectType_Id(String id);
}
