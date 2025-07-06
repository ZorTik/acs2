package me.zort.acs.data.repository;

import me.zort.acs.api.data.repository.GrantRepository;
import me.zort.acs.data.entity.GrantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaGrantRepository extends JpaRepository<GrantEntity, UUID>, JpaGrantRepositoryCustom, GrantRepository {
}
