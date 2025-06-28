package me.zort.acs.domain.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@UtilityClass
public final class PageUtils {

    /**
     * Converts the given list into a {@link Page} object using the provided {@link Pageable} for pagination.
     *
     * @param list the source list to paginate
     * @param pageable the pagination information
     * @param <T> the type of elements in the list
     * @return a {@link Page} containing the requested page of elements
     */
    public static @NotNull <T> Page<T> toPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());
        List<T> pageContent = start > end ? List.of() : list.subList(start, end);

        return new PageImpl<>(pageContent, pageable, list.size());
    }
}
