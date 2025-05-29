package me.zort.acs.domain.group;

import me.zort.acs.api.domain.operation.AbstractOperationExecutor;
import me.zort.acs.api.domain.operation.OperationValidator;
import me.zort.acs.domain.model.Group;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupOperationExecutor extends AbstractOperationExecutor<Group> {

    public GroupOperationExecutor(List<OperationValidator<?>> validators) {
        super(validators);
    }
}
