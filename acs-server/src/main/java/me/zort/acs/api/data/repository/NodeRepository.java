package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.NodeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NodeRepository extends CrudRepository<NodeEntity, String> {
}
