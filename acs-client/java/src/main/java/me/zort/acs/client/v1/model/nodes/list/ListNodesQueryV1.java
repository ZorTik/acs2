package me.zort.acs.client.v1.model.nodes.list;

import me.zort.acs.client.PathQuery;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class ListNodesQueryV1 implements PathQuery {
    private final Map<String, Object> query;

    public ListNodesQueryV1(final @NotNull Map<String, Object> queryParameters) {
        this.query = Objects.requireNonNull(queryParameters, "queryParameters cannot be null");
    }

    public static @NotNull ListNodesQueryV1 bySubjectType(@NotNull String subjectTypeId) {
        Objects.requireNonNull(subjectTypeId, "subjectTypeId cannot be null");

        return new ListNodesQueryV1(Map.of("subjectType", subjectTypeId));
    }

    @Override
    public Map<String, Object> getQueryAttributes() {
        return Map.copyOf(query);
    }
}
