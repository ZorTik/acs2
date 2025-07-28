package me.zort.acs.api.data.repository;

public interface SaveRepository<E> {

    E save(E entity);
}
