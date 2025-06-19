package me.zort.acs.data.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.entity.AcsEntity;
import me.zort.acs.api.data.service.PersistenceEntityProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class PersistenceEntityProviderImpl implements PersistenceEntityProvider {
    private final EntityManager entityManager;

    @Override
    public <ID, T extends AcsEntity<ID>> T getCachedOrCreate(Class<T> entityClass, Object anyId) {
        @SuppressWarnings("unchecked")
        ID id = (ID) anyId;

        T entity;
        try {
            entity = entityManager.find(entityClass, id);
            if (entity == null) {
                throw new EntityNotFoundException();
            }

            entity.setId(id);
        } catch (EntityNotFoundException e) {
            entity = BeanUtils.instantiateClass(entityClass);
            entity.setId(id);
        }

        return entity;
    }
}
