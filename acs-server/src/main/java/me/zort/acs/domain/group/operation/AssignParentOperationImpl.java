package me.zort.acs.domain.group.operation;

import lombok.Getter;
import me.zort.acs.api.domain.group.operation.AssignParentOperation;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.util.GroupUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class AssignParentOperationImpl implements AssignParentOperation {
    @Getter
    private Group parent;

    public @NotNull AssignParentOperationImpl withParent(Group parent) {
        this.parent = parent;
        return this;
    }

    private void validateOperation(Group group) {
        if (parent != null) {
            if (!parent.getSubjectType().equals(group.getSubjectType())) {
                throw new IllegalArgumentException("Parent group must have the same subject type as this group.");
            }

            if (parent.equals(group)) {
                throw new IllegalArgumentException("A group cannot be its own parent.");
            }

            if (GroupUtils.detectCircularDependency(parent, group)) {
                throw new IllegalArgumentException("Setting this group as a parent would create a circular dependency.");
            }
        }
    }

    @Override
    public void execute(Group group) {
        validateOperation(group);
        group.setParent(parent);
    }

    @Override
    public boolean isAutoCommit() {
        return true;
    }
}
