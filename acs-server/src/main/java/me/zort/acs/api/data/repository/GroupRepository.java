package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.id.GroupId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GroupRepository extends SaveRepository<GroupEntity> {

    Optional<GroupEntity> findById(GroupId id);

    List<GroupEntity> findAllBySubjectType_Id(String id);
}
