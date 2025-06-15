package me.zort.acs_plane.api.domain.definitions;

import me.zort.acs.core.domain.definitions.model.*;

/**
 * Factory interface for creating various definitions models.
 * <p>
 * Provides methods to instantiate different types of definition models,
 * such as subject types, default grants, nodes, and groups.
 * </p>
 */
public interface DefinitionsObjectFactory {

    /**
     * Creates a new instance of {@link DefinitionsModel}.
     *
     * @return a new definitions model
     */
    DefinitionsModel createModel();

    /**
     * Creates a new instance of {@link SubjectTypeDefinitionModel} with the specified ID.
     *
     * @param id the identifier of the subject type
     * @return a new subject type definition model
     */
    SubjectTypeDefinitionModel createSubjectTypeModel(String id);

    /**
     * Creates a new instance of {@link DefaultGrantsDefinitionModel} for the given accessor and accessed type IDs.
     *
     * @param accessorType the type of the accessor
     * @param accessedType the type of the accessed subject
     * @return a new default grants definition model
     */
    DefaultGrantsDefinitionModel createDefaultGrantsModel(
            SubjectTypeDefinitionModel accessorType, SubjectTypeDefinitionModel accessedType);

    /**
     * Creates a new instance of {@link NodeDefinitionModel} with the specified value.
     *
     * @param value the value of the node
     * @return a new node definition model
     */
    NodeDefinitionModel createNodeModel(String value);

    /**
     * Creates a new instance of {@link GroupDefinitionModel} with the specified name and parent name.
     *
     * @param name the name of the group
     * @param parentName the name of the parent group
     * @return a new group definition model
     */
    GroupDefinitionModel createGroupModel(String name, String parentName);
}
