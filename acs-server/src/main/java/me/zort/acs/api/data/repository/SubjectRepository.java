package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.id.SubjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SubjectRepository extends CrudRepository<SubjectEntity, SubjectId> {
}
