package me.zort.acs.domain.access;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.AccessQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class AccessQueryServiceImpl implements AccessQueryService {
    private final List<AccessQueryable> sources;

    @Override
    public <T> Page<T> performAggregatedQuery(
            BiFunction<AccessQueryable, Pageable, Page<T>> queryFunction, Pageable pageable) {
        long requestedOffset = pageable.getOffset();

        List<T> cache = new ArrayList<>(pageable.getPageSize());
        long aggregatedQueryableOffset = 0;
        for (AccessQueryable queryable : sources) {
            Page<T> lookupPage = queryFunction.apply(queryable, PageRequest.of(0, 1));

            long totalElementsInSource = lookupPage.getTotalElements();
            if (requestedOffset >= aggregatedQueryableOffset
                    && requestedOffset + pageable.getPageSize() < aggregatedQueryableOffset + totalElementsInSource) {
                // If the current offset is within the range of this queryable and the page is fully in the source,
                // we can fetch the page from it.
                long pageOffset = requestedOffset - aggregatedQueryableOffset;
                int pageSize = pageable.getPageSize();

                return queryFunction.apply(queryable, PageRequest.of((int) (pageOffset / pageSize), pageSize));
            } else if (requestedOffset >= aggregatedQueryableOffset
                    // The page starts in this queryable
                    && requestedOffset < aggregatedQueryableOffset + totalElementsInSource
                    && requestedOffset + pageable.getPageSize() >= aggregatedQueryableOffset + totalElementsInSource) {
                // Page is larger than the source, so we need to fetch the partial page.

                long localOffset = requestedOffset - aggregatedQueryableOffset;
                int remaining = pageable.getPageSize();

                PageRequest partialPageable = PageRequest.of((int) (localOffset / pageable.getPageSize()), remaining);
                Page<T> partialPage = queryFunction.apply(queryable, partialPageable);

                cache.addAll(partialPage.getContent());
                remaining -= partialPage.getContent().size();

                // Continue collecting from next queryables
                for (int j = sources.indexOf(queryable) + 1; j < sources.size() && remaining > 0; j++) {
                    AccessQueryable nextQueryable = sources.get(j);
                    Page<T> nextPage = queryFunction.apply(nextQueryable, PageRequest.of(0, remaining));
                    cache.addAll(nextPage.getContent());
                    remaining -= nextPage.getContent().size();
                }

                return new PageImpl<>(cache, pageable, totalElementsInSource + lookupPage.getTotalElements());
            }

            aggregatedQueryableOffset += totalElementsInSource;
        }

        return Page.empty(pageable);
    }
}
