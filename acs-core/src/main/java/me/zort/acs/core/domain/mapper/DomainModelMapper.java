package me.zort.acs.core.domain.mapper;

public interface DomainModelMapper<D, P> extends DomainToPersistenceMapper<D, P>, PersistenceToDomainMapper<P, D> {
    // This interface combines the two mapper interfaces
    // to provide a single point of mapping between domain and persistence models.
}
