package me.zort.acs.api.domain.mapper;

public interface DomainToPersistenceMapper<D, P> {

    default P toPersistence(D domain) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
