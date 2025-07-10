package me.zort.acs.client.v1;

import com.google.common.collect.Maps;
import me.zort.acs.client.*;
import me.zort.acs.client.http.HttpMethod;
import me.zort.acs.client.http.adapter.HttpAdapter;
import me.zort.acs.client.http.model.Page;
import me.zort.acs.client.http.serializer.HttpSerializer;
import me.zort.acs.client.v1.interceptor.CommonFailuresInterceptor;
import me.zort.acs.client.v1.model.BasicResponseV1;
import me.zort.acs.client.v1.model.SubjectV1;
import me.zort.acs.client.v1.model.check.CheckAccessRequestV1;
import me.zort.acs.client.v1.model.check.CheckAccessResponseV1;
import me.zort.acs.client.v1.model.grant.GrantAccessRequestV1;
import me.zort.acs.client.v1.model.grant.GrantAccessResponseV1;
import me.zort.acs.client.v1.model.nodes.granted.GrantedNodesResponseV1;
import me.zort.acs.client.v1.model.nodes.list.ListNodesResponseV1;
import me.zort.acs.client.v1.model.revoke.RevokeAccessRequestV1;
import me.zort.acs.client.v1.model.revoke.RevokeAccessResponseV1;
import me.zort.acs.client.v1.model.subjects.list.ListSubjectsResponseV1;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AcsClientV1 extends AbstractAcsClient {
    private static final String PREFIX = "/v1";
    private static final String CHECK_ACCESS_URL = PREFIX + "/access/check";
    private static final String GRANT_ACCESS_URL = PREFIX + "/access/grant";
    private static final String REVOKE_ACCESS_URL = PREFIX + "/access/revoke";
    private static final String LIST_NODES_URL = PREFIX + "/nodes";
    private static final String LIST_NODES_GRANTED_URL = PREFIX + "/nodes/granted";
    private static final String LIST_RESOURCES_GRANTED_URL = PREFIX + "/resources/granted";
    private static final String REGISTER_RESOURCE_URL = PREFIX + "/resource/register";
    private static final String UNREGISTER_RESOURCE_URL = PREFIX + "/resource/unregister";

    public AcsClientV1(String baseUrl, HttpAdapter httpAdapter, HttpSerializer httpSerializer) {
        super(baseUrl, httpAdapter, httpSerializer, List.of(new CommonFailuresInterceptor(httpSerializer)));
    }

    // v1 API uses a specific serialization format for subjects in queries.
    private String serializeSubjectToQuery(@NotNull AcsSubjectResolvable subject) {
        return subject.getGroup() + ":" + subject.getId();
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
            final @NotNull AcsSubjectResolvable resource,
            final @NotNull Set<AcsNodeResolvable> nodes, final @NotNull Set<AcsGroupResolvable> groups) {
        Set<String> values = nodes
                .stream()
                .map(AcsNodeResolvable::getValue).collect(Collectors.toSet());
        Set<String> groupsValues = groups
                .stream()
                .map(AcsGroupResolvable::getName).collect(Collectors.toSet());

        return executeRequest(GrantAccessResponseV1.class, (builder, serializer) -> builder
                .method(HttpMethod.POST)
                .path(GRANT_ACCESS_URL)
                .body(serializer.apply(new GrantAccessRequestV1(accessor, resource, values, groupsValues))));
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
            final @NotNull AcsSubjectResolvable resource,
            final @NotNull Set<AcsNodeResolvable> nodes, final @NotNull Set<AcsGroupResolvable> groups) {
        Set<String> values = nodes
                .stream()
                .map(AcsNodeResolvable::getValue).collect(Collectors.toSet());
        Set<String> groupsValues = groups
                .stream()
                .map(AcsGroupResolvable::getName).collect(Collectors.toSet());

        return executeRequest(RevokeAccessResponseV1.class, (builder, serializer) -> builder
                .method(HttpMethod.POST)
                .path(REVOKE_ACCESS_URL)
                .body(serializer.apply(new RevokeAccessRequestV1(accessor, resource, values, groupsValues))));
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
                .method(HttpMethod.GET)
                .path(LIST_NODES_GRANTED_URL)
                .queryAttributes(Map.of(
                        "accessor", serializeSubjectToQuery(accessor),
                        "resource", serializeSubjectToQuery(resource))));
    }

    /**
     * Registers a new resource (subject) in the ACS system.
     *
     * @param subject the subject to register as a resource
     * @return a response indicating the result of the registration
     */
    public @NotNull BasicResponseV1 registerResource(final @NotNull AcsSubjectResolvable subject) {
        return executeRequest(BasicResponseV1.class, (builder, serializer) -> builder
                .method(HttpMethod.POST)
                .path(REGISTER_RESOURCE_URL)
                .body(serializer.apply(new SubjectV1(subject.getGroup(), String.valueOf(subject.getId())))));
    }

    /**
     * Unregisters a resource (subject) from the ACS system.
     *
     * @param subject the subject to unregister as a resource
     * @return a response indicating the result of the unregistration
     */
    public @NotNull BasicResponseV1 unregisterResource(final @NotNull AcsSubjectResolvable subject) {
        return executeRequest(BasicResponseV1.class, (builder, serializer) ->  builder
                .method(HttpMethod.POST)
                .path(UNREGISTER_RESOURCE_URL)
                .body(serializer.apply(new SubjectV1(subject.getGroup(), String.valueOf(subject.getId())))));
    }

    /**
     * Returns a paginated list of resources (subjects) granted to the specified accessor,
     * filtered by subject type, groups, and nodes.
     *
     * @param accessor the subject for whom granted resources are queried
     * @param subjectType the type of the target subjects
     * @param groups the set of groups to filter by (can be empty)
     * @param nodes the set of nodes to filter by (can be empty)
     * @param page the pagination information
     * @return a response containing the list of granted resources
     */
    public @NotNull ListSubjectsResponseV1 listResourcesGranted(
            final @NotNull AcsSubjectResolvable accessor,
            String subjectType, Set<AcsGroupResolvable> groups, Set<AcsNodeResolvable> nodes, Page page) {
        String groupsQuery = groups.isEmpty() ? "" :
                groups.stream().map(AcsGroupResolvable::getName).collect(Collectors.joining(","));
        String nodesQuery = nodes.isEmpty() ? "" :
                nodes.stream().map(AcsNodeResolvable::getValue).collect(Collectors.joining(","));

        Map<String, String> queryAttributes = Map.of(
                "accessor", serializeSubjectToQuery(accessor),
                "subjectType", subjectType,
                "groups", groupsQuery,
                "nodes", nodesQuery);
        page.applyToQuery(queryAttributes);

        return executeRequest(ListSubjectsResponseV1.class, (builder, serializer) -> builder
                .method(HttpMethod.GET)
                .path(LIST_RESOURCES_GRANTED_URL)
                .queryAttributes(queryAttributes));
    }
}
