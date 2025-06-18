package me.zort.acs.api.data.service;

public interface PersistenceEntityProvider {

    <T> T getCachedOrCreate(Class<T> entityClass, Object id);
}
