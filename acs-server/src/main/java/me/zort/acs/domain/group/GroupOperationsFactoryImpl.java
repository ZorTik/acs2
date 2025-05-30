package me.zort.acs.domain.group;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.operation.Operation;
import me.zort.acs.api.domain.group.GroupOperationsFactory;
import me.zort.acs.domain.group.operation.AssignNodesOperationImpl;
import me.zort.acs.domain.group.operation.AssignParentOperationImpl;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class GroupOperationsFactoryImpl implements GroupOperationsFactory {
    private final ApplicationContext applicationContext;

    @Override
    public Operation<Group> assignParent(Group parent) {
        return applicationContext.getBean(AssignParentOperationImpl.class)
                .withParent(parent);
    }

    @Override
    public Operation<Group> assignNodes(Collection<Node> nodes) {
        return applicationContext.getBean(AssignNodesOperationImpl.class)
                .withNodes(nodes);
    }
}
