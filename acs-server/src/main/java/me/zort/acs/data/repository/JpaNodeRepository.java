package me.zort.acs.data.repository;

import me.zort.acs.api.data.repository.NodeRepository;
import me.zort.acs.data.entity.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaNodeRepository extends JpaRepository<NodeEntity, String>, NodeRepository {

}
