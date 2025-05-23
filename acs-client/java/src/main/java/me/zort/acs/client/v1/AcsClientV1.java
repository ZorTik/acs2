package me.zort.acs.client.v1;

import com.google.common.collect.Maps;
import me.zort.acs.client.AbstractAcsClient;
import me.zort.acs.client.AcsNodeResolvable;
import me.zort.acs.client.AcsSubjectResolvable;
import me.zort.acs.client.PathQuery;
import me.zort.acs.client.http.HttpMethod;
import me.zort.acs.client.http.adapter.HttpAdapter;
import me.zort.acs.client.http.builder.ListNodesQueryBuilder;
import me.zort.acs.client.http.exception.InvalidListNodesQueryException;
import me.zort.acs.client.http.model.check.CheckAccessResponse;
import me.zort.acs.client.http.model.grant.GrantAccessResponse;
import me.zort.acs.client.http.model.nodes.granted.GrantedNodesResponse;
import me.zort.acs.client.http.model.nodes.list.ListNodesQuery;
import me.zort.acs.client.http.model.revoke.RevokeAccessResponse;
import me.zort.acs.client.v1.builder.ListNodesQueryBuilderV1;
import me.zort.acs.client.http.model.nodes.list.ListNodesResponse;
import me.zort.acs.client.http.serializer.HttpSerializer;
import me.zort.acs.client.v1.interceptor.CommonFailuresInterceptor;
import me.zort.acs.client.v1.model.check.CheckAccessRequestV1;
import me.zort.acs.client.v1.model.check.CheckAccessResponseV1;
import me.zort.acs.client.v1.model.grant.GrantAccessRequestV1;
import me.zort.acs.client.v1.model.grant.GrantAccessResponseV1;
import me.zort.acs.client.v1.model.nodes.granted.GrantedNodesRequestV1;
import me.zort.acs.client.v1.model.nodes.granted.GrantedNodesResponseV1;
import me.zort.acs.client.v1.model.nodes.list.ListNodesResponseV1;
import me.zort.acs.client.v1.model.revoke.RevokeAccessRequestV1;
import me.zort.acs.client.v1.model.revoke.RevokeAccessResponseV1;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AcsClientV1 extends AbstractAcsClient {
    private static final String PREFIX = "/v1";
    private static final String CHECK_ACCESS_URL = PREFIX + "/access/check";
    private static final String GRANT_ACCESS_URL = PREFIX + "/access/grant";
    private static final String LIST_NODES_URL = PREFIX + "/nodes";
    private static final String LIST_NODES_GRANTED_URL = PREFIX + "/nodes/granted";

    public AcsClientV1(String baseUrl, HttpAdapter httpAdapter, HttpSerializer httpSerializer) {
        super(baseUrl, httpAdapter, httpSerializer, List.of(new CommonFailuresInterceptor(httpSerializer)));
    }

    @Override
    public @NotNull CheckAccessResponse checkAccess(
            final @NotNull AcsSubjectResolvable accessor,
            final @NotNull AcsSubjectResolvable accessed, final @NotNull Set<AcsNodeResolvable> nodes) {
        Set<String> values = nodes
                .stream()
                .map(AcsNodeResolvable::getValue).collect(Collectors.toSet());

        return executeRequest(CheckAccessResponseV1.class, (builder, serializer) -> builder
                .method(HttpMethod.POST)
                .path(CHECK_ACCESS_URL)
                .body(serializer.apply(new CheckAccessRequestV1(accessor, accessed, values))));
    }

    @Override
    public @NotNull GrantAccessResponse grantAccess(
            final @NotNull AcsSubjectResolvable accessor,
            final @NotNull AcsSubjectResolvable resource, @NotNull Set<AcsNodeResolvable> nodes) {
        Set<String> values = nodes
                .stream()
                .map(AcsNodeResolvable::getValue).collect(Collectors.toSet());

        return executeRequest(GrantAccessResponseV1.class, (builder, serializer) -> builder
                .method(HttpMethod.POST)
                .path(GRANT_ACCESS_URL)
                .body(serializer.apply(new GrantAccessRequestV1(accessor, resource, values))));
    }

    @Override
    public @NotNull RevokeAccessResponse revokeAccess(
            final @NotNull AcsSubjectResolvable accessor,
            final @NotNull AcsSubjectResolvable resource, @NotNull Set<AcsNodeResolvable> nodes) {
        Set<String> values = nodes
                .stream()
                .map(AcsNodeResolvable::getValue).collect(Collectors.toSet());

        return executeRequest(RevokeAccessResponseV1.class, (builder, serializer) -> builder
                .method(HttpMethod.POST)
                .path(GRANT_ACCESS_URL)
                .body(serializer.apply(new RevokeAccessRequestV1(accessor, resource, values))));
    }

    @Override
    public @NotNull ListNodesResponse listNodes(@NotNull ListNodesQuery query) throws InvalidListNodesQueryException {
        if (query instanceof PathQuery pathQuery) {
            Map<String, Object> queryAttributes = pathQuery.getQueryAttributes();

            return executeRequest(ListNodesResponseV1.class, (builder, serializer) -> builder
                    .method(HttpMethod.GET)
                    .path(LIST_NODES_URL)
                    .queryAttributes(Maps.transformValues(queryAttributes, String::valueOf)));
        } else {
            throw new InvalidListNodesQueryException(query);
        }
    }

    @Override
    public @NotNull GrantedNodesResponse listNodesWithGrantState(
            final @NotNull AcsSubjectResolvable accessor, @NotNull AcsSubjectResolvable resource) {
        return executeRequest(GrantedNodesResponseV1.class, (builder, serializer) -> builder
                .method(HttpMethod.POST)
                .path(LIST_NODES_GRANTED_URL)
                .body(serializer.apply(new GrantedNodesRequestV1(accessor, resource))));
    }

    @Override
    public @NotNull ListNodesQueryBuilder listNodesQueryBuilder() {
        return new ListNodesQueryBuilderV1();
    }
}
