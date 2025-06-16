package me.zort.acs_plane.domain.definitions;

import me.zort.acs.core.domain.definitions.model.*;
import me.zort.acs_plane.api.domain.definitions.DefinitionsObjectFactory;
import me.zort.acs_plane.domain.definitions.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DefinitionsObjectFactoryImpl implements DefinitionsObjectFactory {

    @Override
    public DefinitionsModel createModel() {
        return new PlaneDefinitionsModel(new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public SubjectTypeDefinitionModel createSubjectTypeModel(String id) {
        return new PlaneSubjectTypeDefinitionModel(id, new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public DefaultGrantsDefinitionModel createDefaultGrantsModel(
            SubjectTypeDefinitionModel accessorType, SubjectTypeDefinitionModel accessedType) {
        return new PlaneDefaultGrantsDefinitionModel(accessorType, accessedType, new ArrayList<>());
    }

    @Override
    public NodeDefinitionModel createNodeModel(String value) {
        return new PlaneNodeDefinitionModel(value);
    }

    @Override
    public GroupDefinitionModel createGroupModel(String name, String parentName) {
        return new PlaneGroupDefinitionModel(name, parentName, new ArrayList<>());
    }
}
