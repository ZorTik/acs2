package me.zort.acs.domain.access;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.AccessQueryService;
import me.zort.acs.api.domain.access.AggregatedAccessQuery;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class AccessQueryServiceImpl implements AccessQueryService {
    private final List<AccessQueryable> sources;

    /**
     * Performs a partial query across multiple queryables.
     *
     * @param query The function to query the data from the queryable.
     * @param pageable The requested pageable object.
     * @param aggregatedQueryableOffset Cumulative offset of all previous queryables.
     * @param cache A cache to store the results as they are fetched.
     * @param queryable The current queryable (source) being processed.
     * @param lookupPage The page fetched from the current queryable to determine the total elements and offset.
     * @return A page containing the results from the queryables.
     * @param <T> The type of the elements in the page.
     */
    private <T> @NotNull Page<T> queryPartially(
            AggregatedAccessQuery<T> query,
            Pageable pageable,
            long aggregatedQueryableOffset, List<T> cache, AccessQueryable queryable, Page<T> lookupPage) {
        final long requestedOffset = pageable.getOffset();
        final long totalElementsInSource = lookupPage.getTotalElements();

        long localOffset = requestedOffset - aggregatedQueryableOffset;
        int remaining = pageable.getPageSize();

        PageRequest partialPageable = PageRequest.of((int) (localOffset / pageable.getPageSize()), remaining);
        Page<T> partialPage = query.perform(queryable, partialPageable);

        cache.addAll(partialPage.getContent());
        remaining -= partialPage.getContent().size();

        // Continue collecting from next queryables
        for (int j = sources.indexOf(queryable) + 1; j < sources.size() && remaining > 0; j++) {
            AccessQueryable nextQueryable = sources.get(j);
            Page<T> nextPage = query.perform(nextQueryable, PageRequest.of(0, remaining));
            cache.addAll(nextPage.getContent());
            remaining -= nextPage.getContent().size();
        }

        return new PageImpl<>(cache, pageable, totalElementsInSource + lookupPage.getTotalElements());
    }

    /**
     * Queries the data fully from a single queryable.
     * This is used when the requested page is fully contained within a single queryable.
     *
     * @param query The function to query the data from the queryable.
     * @param pageable The requested pageable object.
     * @param queryable The queryable (source) to query from.
     * @param aggregatedQueryableOffset Cumulative offset of all previous queryables.
     * @return A page containing the results from the queryable.
     * @param <T> The type of the elements in the page.
     */
    private static <T> Page<T> queryFully(
            AggregatedAccessQuery<T> query,
            Pageable pageable, AccessQueryable queryable, long aggregatedQueryableOffset) {
        final long requestedOffset = pageable.getOffset();

        long pageOffset = requestedOffset - aggregatedQueryableOffset;
        int pageSize = pageable.getPageSize();

        return query.perform(queryable, PageRequest.of((int) (pageOffset / pageSize), pageSize));
    }

    /**
     * @see AccessQueryService#performAggregatedQuery(AggregatedAccessQuery, Pageable)
     */
    @Override
    public <T> Page<T> performAggregatedQuery(
            AggregatedAccessQuery<T> query, Pageable pageable) {
        final long requestedOffset = pageable.getOffset();

        List<T> cache = new ArrayList<>(pageable.getPageSize());
        long aggregatedQueryableOffset = 0;
        for (AccessQueryable queryable : sources) {
            Page<T> lookupPage = query.perform(queryable, PageRequest.of(0, 1));

            final long totalElementsInSource = lookupPage.getTotalElements();
            if (requestedOffset >= aggregatedQueryableOffset
                    && requestedOffset + pageable.getPageSize() < aggregatedQueryableOffset + totalElementsInSource) {
                // If the current offset is within the range of this queryable and the page is fully in the source,
                // we can fetch the page from it.
                return queryFully(query, pageable, queryable, aggregatedQueryableOffset);
            } else if (requestedOffset >= aggregatedQueryableOffset
                    // The page starts in this queryable
                    && requestedOffset < aggregatedQueryableOffset + totalElementsInSource
                    && requestedOffset + pageable.getPageSize() >= aggregatedQueryableOffset + totalElementsInSource) {
                // The page is larger than the source, so we need to fetch the partial page.
                return queryPartially(query, pageable, aggregatedQueryableOffset, cache, queryable, lookupPage);
            }

            aggregatedQueryableOffset += totalElementsInSource;
        }

        return Page.empty(pageable);
    }
}
