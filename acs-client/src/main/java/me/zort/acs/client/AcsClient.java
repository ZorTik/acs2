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

public interface AcsClient {

    static @NotNull AcsClientBuilderV1 v1() {
        return new AcsClientBuilderV1();
    }

    @NotNull
    CheckAccessResponse checkAccess(
            final @NotNull AcsSubjectResolvable accessor,
            final @NotNull AcsSubjectResolvable accessed, final @NotNull Set<AcsNodeResolvable> nodes);

    @NotNull
    GrantAccessResponse grantAccess(
            final @NotNull AcsSubjectResolvable accessor,
            final @NotNull AcsSubjectResolvable resource, final @NotNull Set<AcsNodeResolvable> nodes);

    @NotNull
    RevokeAccessResponse revokeAccess(
            final @NotNull AcsSubjectResolvable accessor,
            final @NotNull AcsSubjectResolvable resource, final @NotNull Set<AcsNodeResolvable> nodes);

    @NotNull
    ListNodesResponse listNodes(final @NotNull ListNodesQuery query) throws InvalidListNodesQueryException;

    @NotNull
    GrantedNodesResponse listNodesWithGrantState(
            final @NotNull AcsSubjectResolvable accessor, final @NotNull AcsSubjectResolvable resource);

    @NotNull
    ListNodesQueryBuilder listNodesQueryBuilder();
}
