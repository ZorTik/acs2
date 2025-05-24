package me.zort.acs.domain.access.validator;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.service.NodeService;
import me.zort.acs.domain.model.Node;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.model.SubjectType;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class AssignedNodeRequestValidator implements AccessRequestValidator {
    private final NodeService nodeService;

    @Override
    public @Nullable String validate(SubjectLike from, SubjectLike to, Node node) {
        SubjectType toSubjectType = to.getSubjectType();

        if (!nodeService.isNodeAssigned(node, toSubjectType)) {
            return "Resource's subject type (" + toSubjectType.getId() + ") " +
                    "does not contain provided node (" + node.getValue() + ")!";
        }
        return null;
    }
}
