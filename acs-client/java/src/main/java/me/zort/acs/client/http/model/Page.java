package me.zort.acs.client.http.model;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Page {
    private final int page;
    private final int pageSize;

    public static @NotNull Page of(int page, int pageSize) {
        Preconditions.checkArgument(page >= 0, "Page number must be greater than or equal to zero");
        Preconditions.checkArgument(pageSize > 0, "Page size must be greater than zero");

        return new Page(page, pageSize);
    }

    public void applyToQuery(Map<String, String> query) {
        Preconditions.checkNotNull(query, "Query map cannot be null");

        query.put("page", String.valueOf(page));
        query.put("pageSize", String.valueOf(pageSize));
    }
}
