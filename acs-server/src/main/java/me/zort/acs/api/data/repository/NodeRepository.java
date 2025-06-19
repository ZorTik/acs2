package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.NodeEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface NodeRepository {

    NodeEntity save(NodeEntity entity);

    Optional<NodeEntity> findById(String id);

    boolean existsById(String id);
}
