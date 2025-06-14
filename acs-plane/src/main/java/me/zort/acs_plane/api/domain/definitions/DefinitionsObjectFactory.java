package me.zort.acs_plane.api.domain.definitions;

import me.zort.acs.core.domain.definitions.model.*;

public interface DefinitionsObjectFactory {

    DefinitionsModel createModel();

    SubjectTypeDefinitionModel createSubjectTypeModel(String id);

    DefaultGrantsDefinitionModel createDefaultGrantsModel(String accessorTypeId, String accessedTypeId);

    NodeDefinitionModel createNodeModel(String value);

    GroupDefinitionModel createGroupModel(String name, String parentName);
}
