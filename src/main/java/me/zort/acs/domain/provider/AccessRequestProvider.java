package me.zort.acs.domain.provider;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.model.AccessRequest;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectLike;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.service.NodeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class AccessRequestProvider {
    private final NodeService nodeService;

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
    public @NotNull AccessRequest getAccessRequest(
            SubjectLike from, SubjectLike to, Node node) throws IllegalArgumentException {
        SubjectType toSubjectType = to.getSubjectType();

        if (!nodeService.isNodeAssigned(node, toSubjectType)) {
            throw new IllegalArgumentException("Resource's subject type (" + toSubjectType.getId() + ") " +
                            "does not contain provided node (" + node.getValue() + ")!");
        }
        return new AccessRequest(from, to, node);
    }
}
