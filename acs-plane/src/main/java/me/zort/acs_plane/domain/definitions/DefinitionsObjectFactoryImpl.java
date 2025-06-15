package me.zort.acs_plane.domain.definitions;

import me.zort.acs.core.domain.definitions.model.*;
import me.zort.acs_plane.api.domain.definitions.DefinitionsObjectFactory;
import org.springframework.stereotype.Service;

@Service
public class DefinitionsObjectFactoryImpl implements DefinitionsObjectFactory {

    @Override
    public DefinitionsModel createModel() {
        // TODO
        return null;
    }

    @Override
    public SubjectTypeDefinitionModel createSubjectTypeModel(String id) {
        // TODO
        return null;
    }

    @Override
    public DefaultGrantsDefinitionModel createDefaultGrantsModel(
            SubjectTypeDefinitionModel accessorType, SubjectTypeDefinitionModel accessedType) {
        return null;
    }

    @Override
    public NodeDefinitionModel createNodeModel(String value) {
        // TODO
        return null;
    }

    @Override
    public GroupDefinitionModel createGroupModel(String name, String parentName) {
        // TODO
        return null;
    }
}
