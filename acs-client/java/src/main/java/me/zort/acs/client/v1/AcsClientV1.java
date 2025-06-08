package me.zort.acs.client.v1;

import com.google.common.collect.Maps;
import me.zort.acs.client.AbstractAcsClient;
import me.zort.acs.client.AcsNodeResolvable;
import me.zort.acs.client.AcsSubjectResolvable;
import me.zort.acs.client.PathQuery;
import me.zort.acs.client.http.HttpMethod;
import me.zort.acs.client.http.adapter.HttpAdapter;
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

    /**
     * Checks whether the accessor has access to the accessed subject based on specified nodes.
     *
     * @param accessor the subject requesting access
     * @param accessed the target subject or resource being accessed
     * @param nodes    a set of nodes representing permissions to check
     * @return the result of the access check
     */
    public @NotNull CheckAccessResponseV1 checkAccess(
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

    /**
     * Grants access for the accessor to the specified resource on given nodes.
     *
     * @param accessor the subject to whom access is granted
     * @param resource the target resource or subject
     * @param nodes    a set of nodes representing permissions to grant
     * @return the result of the grant operation
     */
    public @NotNull GrantAccessResponseV1 grantAccess(
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

    /**
     * Revokes access for the accessor to the specified resource on given nodes.
     *
     * @param accessor the subject whose access is revoked
     * @param resource the target resource or subject
     * @param nodes    a set of nodes representing permissions to revoke
     * @return the result of the revoke operation
     */
    public @NotNull RevokeAccessResponseV1 revokeAccess(
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

    /**
     * Queries available nodes based on the specified query parameters.
     *
     * @param query the parameters for the nodes query
     * @return a response containing the list of nodes
     */
    public @NotNull ListNodesResponseV1 listNodes(@NotNull PathQuery query) {
        Map<String, Object> queryAttributes = query.getQueryAttributes();

        return executeRequest(ListNodesResponseV1.class, (builder, serializer) -> builder
                .method(HttpMethod.GET)
                .path(LIST_NODES_URL)
                .queryAttributes(Maps.transformValues(queryAttributes, String::valueOf)));
    }

    /**
     * Returns a list of nodes along with their grant state for a given accessor and resource.
     *
     * @param accessor the subject whose access is checked
     * @param resource the target resource or subject
     * @return a response with nodes and their grant states
     */
    public @NotNull GrantedNodesResponseV1 listNodesWithGrantState(
            final @NotNull AcsSubjectResolvable accessor, @NotNull AcsSubjectResolvable resource) {
        return executeRequest(GrantedNodesResponseV1.class, (builder, serializer) -> builder
                .method(HttpMethod.POST)
                .path(LIST_NODES_GRANTED_URL)
                .body(serializer.apply(new GrantedNodesRequestV1(accessor, resource))));
    }
}
