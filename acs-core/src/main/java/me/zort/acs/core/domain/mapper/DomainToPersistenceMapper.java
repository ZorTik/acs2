package me.zort.acs.core.domain.mapper;

public interface DomainToPersistenceMapper<D, P> {

    default P toPersistence(D domain) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
