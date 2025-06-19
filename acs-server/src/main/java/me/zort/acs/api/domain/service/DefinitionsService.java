package me.zort.acs.api.domain.service;

import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;

import java.util.Set;

/**
 * Service interface for managing and retrieving access control definitions.
 * <p>
 * Provides methods to refresh definitions and obtain default granted nodes and groups
 * based on accessor and accessed subject types.
 * </p>
 */
public interface DefinitionsService {

    /**
     * Refreshes the access control definitions, reloading any cached or stored data.
     */
    void refreshDefinitions();

    /**
     * Returns the set of default granted nodes for the given accessor and accessed subject types.
     *
     * @param accessorType the type of the accessor subject
     * @param accessedType the type of the accessed subject
     * @return a set of default granted {@link Node} instances
     */
    Set<Node> getDefaultGrantedNodes(SubjectType accessorType, SubjectType accessedType);

    /**
     * Returns the set of default granted groups for the given accessor and accessed subject types.
     *
     * @param accessorType the type of the accessor subject
     * @param accessedType the type of the accessed subject
     * @return a set of default granted {@link Group} instances
     */
    Set<Group> getDefaultGrantedGroups(SubjectType accessorType, SubjectType accessedType);
}
