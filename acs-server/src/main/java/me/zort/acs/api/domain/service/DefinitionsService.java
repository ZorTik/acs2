package me.zort.acs.api.domain.service;

import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;

import java.util.Set;

public interface DefinitionsService {

    void refreshDefinitions();

    Set<Node> getDefaultGrantedNodes(SubjectType accessorType, SubjectType accessedType);

    Set<Group> getDefaultGrantedGroups(SubjectType accessorType, SubjectType accessedType);
}
