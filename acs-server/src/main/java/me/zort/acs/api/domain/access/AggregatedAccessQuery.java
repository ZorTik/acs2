package me.zort.acs.api.domain.access;

import me.zort.acs.domain.access.AccessQueryable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for performing aggregated access queries with pagination support.
 * <p>
 * Implementations of this interface define how to execute an aggregated query
 * on a given {@link AccessQueryable} source and return the results as a paginated {@link org.springframework.data.domain.Page}.
 * </p>
 *
 * @param <T> the type of elements returned in the page
 */
public interface AggregatedAccessQuery<T> {

    /**
     * Performs an aggregated query on the provided {@link AccessQueryable} source,
     * returning the results as a paginated {@link org.springframework.data.domain.Page}.
     *
     * @param source the access-queryable source to perform the query on
     * @param pageable the pagination information
     * @return a paginated {@link org.springframework.data.domain.Page} of results
     */
    Page<T> perform(AccessQueryable source, Pageable pageable);
}
