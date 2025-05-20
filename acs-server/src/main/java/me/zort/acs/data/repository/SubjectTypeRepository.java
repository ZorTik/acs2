package me.zort.acs.data.repository;

import me.zort.acs.data.entity.SubjectTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectTypeRepository extends JpaRepository<SubjectTypeEntity, String> {

    default SubjectTypeEntity findByIdOrThrow(String id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("Subject type not found"));
    }
}
