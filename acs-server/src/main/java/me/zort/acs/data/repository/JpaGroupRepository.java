package me.zort.acs.data.repository;

import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.id.GroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaGroupRepository extends JpaRepository<GroupEntity, GroupId>, GroupRepository {

    @Override
    List<GroupEntity> findAllBySubjectType_Id(String id);
}
