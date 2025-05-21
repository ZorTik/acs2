package me.zort.acs.client.http.builder;

import me.zort.acs.client.http.model.nodes.list.ListNodesQuery;
import org.jetbrains.annotations.NotNull;

public interface ListNodesQueryBuilder {

    ListNodesQuery bySubject(final @NotNull String subjectId);
}
