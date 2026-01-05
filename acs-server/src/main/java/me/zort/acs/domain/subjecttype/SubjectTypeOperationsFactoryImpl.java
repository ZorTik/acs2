package me.zort.acs.domain.subjecttype;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.operation.Operation;
import me.zort.acs.api.domain.subjecttype.CreateSubjectTypeOptions;
import me.zort.acs.api.domain.subjecttype.SubjectTypeOperationsFactory;
import me.zort.acs.core.model.Node;
import me.zort.acs.core.model.SubjectType;
import me.zort.acs.domain.subjecttype.operation.AssignNodesOperation;
import me.zort.acs.domain.subjecttype.operation.ChangeDynamicGroupsSupportOperation;
import me.zort.acs.domain.subjecttype.operation.InitAndSaveOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class SubjectTypeOperationsFactoryImpl implements SubjectTypeOperationsFactory {
    private final ApplicationContext applicationContext;

    @Override
    public Operation<SubjectType> initAndSave(CreateSubjectTypeOptions options) {
        return applicationContext.getBean(InitAndSaveOperation.class).withOptions(options);
    }

    @Override
    public Operation<SubjectType> assignNodes(Collection<Node> nodes) {
        return applicationContext.getBean(AssignNodesOperation.class).withNodes(nodes);
    }

    @Override
    public Operation<SubjectType> changeDynamicGroupsSupport(boolean dynamicGroupsSupported) {
        return applicationContext.getBean(ChangeDynamicGroupsSupportOperation.class).withValue(dynamicGroupsSupported);
    }
}
