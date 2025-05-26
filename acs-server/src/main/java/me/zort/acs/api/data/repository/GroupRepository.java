package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.id.GroupId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GroupRepository extends CrudRepository<GroupEntity, GroupId> {
}
