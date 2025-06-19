package me.zort.acs.api.data.service;

import me.zort.acs.api.data.entity.AcsEntity;

/**
 * Interface for providing persistence entities, supporting caching.
 * <p>
 * Implementations should return a cached entity if available,
 * or create and cache a new one if not present.
 * </p>
 */
public interface PersistenceEntityProvider {

    /**
     * Returns a cached entity of the given class and ID, or creates and caches a new one if not present.
     *
     * @param entityClass the class of the entity
     * @param id the identifier of the entity
     * @param <T> the type of the entity
     * @return the cached or newly created entity
     */
    <ID, T extends AcsEntity<ID>> T getCachedOrCreate(Class<T> entityClass, Object id);
}
