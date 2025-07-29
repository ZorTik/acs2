package me.zort.acs.api.domain.subjecttype;

import me.zort.acs.api.domain.operation.Operation;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;

import java.util.Collection;

public interface SubjectTypeOperationsFactory {

    Operation<SubjectType> initAndSave(CreateSubjectTypeOptions options);

    Operation<SubjectType> assignNodes(Collection<Node> nodes);

    Operation<SubjectType> changeDynamicGroupsSupport(boolean dynamicGroupsSupported);
}
