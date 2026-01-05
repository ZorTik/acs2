package me.zort.acs.domain.subjecttype.operation;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.operation.AutoCommittableOperation;
import me.zort.acs.api.domain.operation.OperationCallContext;
import me.zort.acs.api.domain.subjecttype.CreateSubjectTypeOptions;
import me.zort.acs.core.model.SubjectType;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Scope("prototype")
@Component
@RequiredArgsConstructor
public class InitAndSaveOperation implements AutoCommittableOperation<SubjectType> {
    private CreateSubjectTypeOptions options = null;

    public @NotNull InitAndSaveOperation withOptions(@NotNull CreateSubjectTypeOptions options) {
        this.options = options;

        return this;
    }

    @Override
    public void execute(SubjectType subjectType, OperationCallContext context) throws RuntimeException {
        Objects.requireNonNull(subjectType, "subjectType cannot be null");
        Objects.requireNonNull(options, "options cannot be null");

        options.getNodes().forEach(subjectType::addNode);
    }
}
