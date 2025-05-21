package me.zort.acs.client.v1.builder;

import me.zort.acs.client.http.builder.ListNodesQueryBuilder;
import me.zort.acs.client.http.model.nodes.list.ListNodesQuery;
import me.zort.acs.client.v1.model.nodes.list.ListNodesQueryV1;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class ListNodesQueryBuilderV1 implements ListNodesQueryBuilder {
    @Override
    public ListNodesQuery bySubject(@NotNull String subjectId) {
        Objects.requireNonNull(subjectId, "subjectId cannot be null");

        return new ListNodesQueryV1(Map.of("subjectType", subjectId));
    }
}
