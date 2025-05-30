package me.zort.acs.api.domain.definitions.model;

import java.util.List;

public interface SubjectTypeDefinitionModel {

    String getId();

    List<NodeDefinitionModel> getNodes();

    List<GroupDefinitionModel> getGroups();
}
