package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.SubjectTypeEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface SubjectTypeRepository {

    SubjectTypeEntity save(SubjectTypeEntity entity);

    void delete(SubjectTypeEntity entity);

    boolean existsById(String id);

    Optional<SubjectTypeEntity> findById(String id);

    List<SubjectTypeEntity> findAll();
}
