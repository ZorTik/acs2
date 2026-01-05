package me.zort.acs.domain.subjecttype.operation;

import me.zort.acs.api.domain.operation.AutoCommittableOperation;
import me.zort.acs.api.domain.operation.OperationCallContext;
import me.zort.acs.core.model.SubjectType;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class ChangeDynamicGroupsSupportOperation implements AutoCommittableOperation<SubjectType> {
    private boolean value = false;

    public @NotNull ChangeDynamicGroupsSupportOperation withValue(boolean value) {
        this.value = value;

        return this;
    }

    @Override
    public void execute(SubjectType subjectType, OperationCallContext context) throws RuntimeException {
        subjectType.setSupportsDynamicGroups(value);
    }
}
