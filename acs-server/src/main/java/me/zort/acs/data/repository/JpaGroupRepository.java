package me.zort.acs.data.repository;

import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaGroupRepository extends JpaRepository<GroupEntity, UUID>, GroupRepository {

    @Override
    @NotNull
    Optional<GroupEntity> findById(@NotNull UUID id);

    @Override
    void deleteById(@NotNull UUID id);

    @Override
    Optional<GroupEntity> findBySubjectTypeAndName(SubjectTypeEntity subjectType, String name);

    @Override
    Optional<GroupEntity> findBySubjectAndName(SubjectEntity subject, String name);

    @Override
    List<GroupEntity> findAllBySubjectType(SubjectTypeEntity subjectType);

    @Override
    List<GroupEntity> findAllBySubject(SubjectEntity subject);
}
