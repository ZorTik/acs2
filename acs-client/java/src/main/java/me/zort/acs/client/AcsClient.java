package me.zort.acs.client;

import me.zort.acs.client.http.builder.ListNodesQueryBuilder;
import me.zort.acs.client.http.exception.InvalidListNodesQueryException;
import me.zort.acs.client.http.model.check.CheckAccessResponse;
import me.zort.acs.client.http.model.grant.GrantAccessResponse;
import me.zort.acs.client.http.model.nodes.granted.GrantedNodesResponse;
import me.zort.acs.client.http.model.nodes.list.ListNodesQuery;
import me.zort.acs.client.http.model.nodes.list.ListNodesResponse;
import me.zort.acs.client.http.model.revoke.RevokeAccessResponse;
import me.zort.acs.client.v1.AcsClientBuilderV1;
import me.zort.acs.client.http.model.NodeStatesResponse;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Main interface for the Access Control Service (ACS) client.
 * Provides operations for access checking, permission management, and querying access nodes.
 */
public interface AcsClient {

    /**
     * Creates a new builder instance for ACS Client version 1.
     *
     * @return a builder for AcsClient v1
     */
    static @NotNull AcsClientBuilderV1 v1() {
        return new AcsClientBuilderV1();
    }

    /**
     * Checks whether the accessor has access to the accessed subject based on specified nodes.
     *
     * @param accessor the subject requesting access
     * @param accessed the target subject or resource being accessed
     * @param nodes    a set of nodes representing permissions to check
     * @return the result of the access check
     */
    @NotNull
    CheckAccessResponse checkAccess(
            final @NotNull AcsSubjectResolvable accessor,
            final @NotNull AcsSubjectResolvable accessed, final @NotNull Set<AcsNodeResolvable> nodes);

    /**
     * Grants access for the accessor to the specified resource on given nodes.
     *
     * @param accessor the subject to whom access is granted
     * @param resource the target resource or subject
     * @param nodes    a set of nodes representing permissions to grant
     * @return the result of the grant operation
     */
    @NotNull
    GrantAccessResponse grantAccess(
            final @NotNull AcsSubjectResolvable accessor,
            final @NotNull AcsSubjectResolvable resource, final @NotNull Set<AcsNodeResolvable> nodes);

    /**
     * Revokes access for the accessor to the specified resource on given nodes.
     *
     * @param accessor the subject whose access is revoked
     * @param resource the target resource or subject
     * @param nodes    a set of nodes representing permissions to revoke
     * @return the result of the revoke operation
     */
    @NotNull
    RevokeAccessResponse revokeAccess(
            final @NotNull AcsSubjectResolvable accessor,
            final @NotNull AcsSubjectResolvable resource, final @NotNull Set<AcsNodeResolvable> nodes);

    /**
     * Queries available nodes based on the specified query parameters.
     *
     * @param query the parameters for the nodes query
     * @return a response containing the list of nodes
     * @throws InvalidListNodesQueryException if the query parameters are invalid
     */
    @NotNull
    ListNodesResponse listNodes(final @NotNull ListNodesQuery query) throws InvalidListNodesQueryException;

    /**
     * Returns a list of nodes along with their grant state for a given accessor and resource.
     *
     * @param accessor the subject whose access is checked
     * @param resource the target resource or subject
     * @return a response with nodes and their grant states
     */
    @NotNull
    GrantedNodesResponse listNodesWithGrantState(
            final @NotNull AcsSubjectResolvable accessor, final @NotNull AcsSubjectResolvable resource);

    /**
     * Creates a new builder instance for constructing ListNodesQuery objects.
     *
     * @return a new ListNodesQueryBuilder instance
     */
    @NotNull
    ListNodesQueryBuilder listNodesQueryBuilder();
}
