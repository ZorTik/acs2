package me.zort.acs.domain.access.validator;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class AssignedHolderRequestValidator implements AccessRequestValidator {

    @Override
    public @Nullable String validate(SubjectLike from, SubjectLike to, RightsHolder rightsHolder) {
        SubjectType toSubjectType = to.getSubjectType();

        if (rightsHolder instanceof Node node && !toSubjectType.containsNode(node)) {
            return "Resource's subject type (" + toSubjectType.getId() + ") " +
                    "does not contain provided node (" + node.getValue() + ")!";
        }
        if (rightsHolder instanceof Group group && !group.getSubjectType().equals(toSubjectType)) {
            return "Resource's subject type (" + toSubjectType.getId() + ") " +
                    "does not contain provided group (" + group.getName() + ")!";
        }
        return null;
    }
}
