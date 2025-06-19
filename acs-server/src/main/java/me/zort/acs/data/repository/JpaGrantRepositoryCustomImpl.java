package me.zort.acs.data.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GroupId;
import me.zort.acs.data.id.SubjectId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaGrantRepositoryCustomImpl implements JpaGrantRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GrantEntity> findAllBetween(SubjectId accessorId, SubjectId accessedId) {
        return entityManager.createQuery(
                "SELECT g FROM acs_grants g WHERE g.accessor.id = :accessorId AND g.accessed.id = :accessedId", GrantEntity.class)
                .setParameter("accessorId", accessorId)
                .setParameter("accessedId", accessedId)
                .getResultList();
    }

    @Override
    public Optional<GrantEntity> findNodeGrant(SubjectId accessorId, SubjectId accessedId, String value) {
        return entityManager.createQuery(
                "SELECT g FROM acs_grants g WHERE g.accessor.id = :accessorId AND g.accessed.id = :accessedId AND g.node.value = :value", GrantEntity.class)
                .setParameter("accessorId", accessorId)
                .setParameter("accessedId", accessedId)
                .setParameter("value", value)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<GrantEntity> findGroupGrant(SubjectId accessorId, SubjectId accessedId, GroupId groupId) {
        return entityManager.createQuery(
                "SELECT g FROM acs_grants g WHERE g.accessor.id = :accessorId AND g.accessed.id = :accessedId AND g.group.id = :groupId", GrantEntity.class)
                .setParameter("accessorId", accessorId)
                .setParameter("accessedId", accessedId)
                .setParameter("groupId", groupId)
                .getResultStream()
                .findFirst();
    }

    @Override
    public int countByAccessorId(SubjectId accessorId) {
        return entityManager.createQuery(
                "SELECT COUNT(g) FROM acs_grants g WHERE g.accessor.id = :accessorId", Long.class)
                .setParameter("accessorId", accessorId)
                .getSingleResult()
                .intValue();
    }
}
