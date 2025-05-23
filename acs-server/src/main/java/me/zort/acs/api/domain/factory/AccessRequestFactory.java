package me.zort.acs.api.domain.factory;

import me.zort.acs.domain.model.AccessRequest;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectLike;
import org.jetbrains.annotations.NotNull;

public interface AccessRequestFactory {

    /**
     * Constructs an AccessRequest object with the given parameters.
     *
     * @param from The accessing object
     * @param to The accessed object
     * @param node The node to check applicability for
     * @return An AccessRequest object with the given parameters
     *
     * @throws IllegalArgumentException if the node is not applicable on the accessed object
     */
    @NotNull AccessRequest createAccessRequest(
            SubjectLike from, SubjectLike to, Node node) throws IllegalArgumentException;
}
