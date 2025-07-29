package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.data.id.GroupId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface GroupRepository extends SaveRepository<GroupEntity> {

    Optional<GroupEntity> findById(UUID id);

    void deleteById(UUID id);

    Optional<GroupEntity> findBySubjectTypeAndName(SubjectTypeEntity subjectType, String name);

    Optional<GroupEntity> findBySubjectAndName(SubjectEntity subject, String name);

    List<GroupEntity> findAllBySubjectType(SubjectTypeEntity subjectType);

    List<GroupEntity> findAllBySubject(SubjectEntity subject);
}
