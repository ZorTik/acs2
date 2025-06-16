package me.zort.acs.core.domain.mapper;

public interface PersistenceToDomainMapper<P, D> {

    default D toDomain(P persistence) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
