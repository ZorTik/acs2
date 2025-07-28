package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.SubjectTypeEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface SubjectTypeRepository extends SaveRepository<SubjectTypeEntity> {

    void deleteById(String id);

    boolean existsById(String id);

    Optional<SubjectTypeEntity> findById(String id);

    List<SubjectTypeEntity> findAll();
}
