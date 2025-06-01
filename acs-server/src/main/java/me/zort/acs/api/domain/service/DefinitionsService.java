package me.zort.acs.api.domain.service;

import me.zort.acs.api.domain.definitions.exception.DefinitionsRefreshException;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;

import java.util.Set;

public interface DefinitionsService {

    void refreshDefinitions() throws DefinitionsRefreshException;

    Set<Node> getDefaultGrantedNodes(SubjectType accessorType, SubjectType accessedType);
}
