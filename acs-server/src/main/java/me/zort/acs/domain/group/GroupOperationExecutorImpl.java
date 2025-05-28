package me.zort.acs.domain.group;

import lombok.extern.slf4j.Slf4j;
import me.zort.acs.api.domain.group.operation.GroupOperation;
import me.zort.acs.api.domain.group.GroupOperationExecutor;
import me.zort.acs.domain.group.exception.IllegalOperationException;
import me.zort.acs.domain.group.validator.GroupOperationValidator;
import me.zort.acs.domain.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GroupOperationExecutorImpl implements GroupOperationExecutor {
    private final List<GroupOperationValidator<?>> validators;

    @Autowired
    public GroupOperationExecutorImpl(List<GroupOperationValidator<?>> validators) {
        this.validators = validators;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <O extends GroupOperation> boolean executeOperation(O operation, Group group) throws IllegalOperationException {
        try {
            validators
                    .stream()
                    .filter(validator ->
                            validator.getOperationClass().isAssignableFrom(operation.getClass()))
                    .map(validator -> (GroupOperationValidator<O>) validator)
                    .findFirst().ifPresent(validator -> validator.validate(operation));

            operation.execute(group);

            return true;
        } catch (RuntimeException e) {
            log.error(
                    "Failed to execute group operation {} on group {}",
                    operation.getClass().getSimpleName(), group.getName(), e);

            return false;
        }
    }
}
