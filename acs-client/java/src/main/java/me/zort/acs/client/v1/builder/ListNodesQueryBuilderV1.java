package me.zort.acs.client.v1.builder;

import me.zort.acs.client.v1.model.nodes.list.ListNodesQueryV1;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class ListNodesQueryBuilderV1 {
    public ListNodesQueryV1 bySubjectType(@NotNull String subjectTypeId) {
        Objects.requireNonNull(subjectTypeId, "subjectTypeId cannot be null");

        return new ListNodesQueryV1(Map.of("subjectType", subjectTypeId));
    }
}
