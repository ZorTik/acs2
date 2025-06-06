package me.zort.acs.domain.group.operation;

import lombok.Getter;
import me.zort.acs.api.domain.group.operation.AssignParentOperation;
import me.zort.acs.domain.model.Group;
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

    @Override
    public void execute(Group group) {
        group.setParent(parent);
    }

    @Override
    public boolean isAutoCommit() {
        return true;
    }
}
