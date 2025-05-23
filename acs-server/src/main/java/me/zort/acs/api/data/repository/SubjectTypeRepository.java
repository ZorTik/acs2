package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.SubjectTypeEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SubjectTypeRepository extends ListCrudRepository<SubjectTypeEntity, String> {

    SubjectTypeEntity findByIdOrThrow(String id);
}
