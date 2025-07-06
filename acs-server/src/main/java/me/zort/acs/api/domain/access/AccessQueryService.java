package me.zort.acs.api.domain.access;

import me.zort.acs.domain.access.AccessQueryable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for performing aggregated queries on {@link AccessQueryable} entities
 * with support for pagination.
 * <p>
 * Provides a method to execute a custom query function that aggregates results
 * and returns them as a paginated {@link org.springframework.data.domain.Page}.
 * </p>
 */
public interface AccessQueryService {

    /**
     * Executes the given query function to perform an aggregated query on {@link AccessQueryable} entities,
     * returning the results as a paginated {@link org.springframework.data.domain.Page}.
     *
     * @param query the function that performs the query, accepting an {@link AccessQueryable} and {@link Pageable}
     * @param pageable the pagination information
     * @param <T> the type of elements returned in the page
     * @return a paginated {@link org.springframework.data.domain.Page} of results
     */
    <T> Page<T> performAggregatedQuery(AggregatedAccessQuery<T> query, Pageable pageable);
}
