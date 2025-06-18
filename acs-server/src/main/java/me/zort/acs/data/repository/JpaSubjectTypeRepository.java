package me.zort.acs.data.repository;

import me.zort.acs.api.data.repository.SubjectTypeRepository;
import me.zort.acs.data.entity.SubjectTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSubjectTypeRepository extends JpaRepository<SubjectTypeEntity, String>, SubjectTypeRepository {
}
