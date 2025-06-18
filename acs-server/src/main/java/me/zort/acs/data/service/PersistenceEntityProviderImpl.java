package me.zort.acs.data.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.service.PersistenceEntityProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class PersistenceEntityProviderImpl implements PersistenceEntityProvider {
    private final EntityManager entityManager;

    @Override
    public <T> T getCachedOrCreate(Class<T> entityClass, Object id) {
        try {
            return entityManager.getReference(entityClass, id);
        } catch (EntityNotFoundException e) {
            return BeanUtils.instantiateClass(entityClass);
        }
    }
}
