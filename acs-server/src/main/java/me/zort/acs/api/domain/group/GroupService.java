package me.zort.acs.api.domain.group;

import me.zort.acs.api.domain.group.exception.GroupAlreadyExistsException;
import me.zort.acs.api.domain.group.exception.GroupCreationDisallowedException;
import me.zort.acs.core.model.SubjectLike;
import me.zort.acs.core.model.Node;
import me.zort.acs.core.model.Subject;
import me.zort.acs.core.model.SubjectType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing groups.
 * <p>
 * If there is a Subject involved, it is most likely a dynamic groups operation
 * returning a group that is associated with a Subject, so called "dynamic".
 */
public interface GroupService {

    /**
     * Create a group.
     *
     * @param options the options for creating the group, such as parent group and nodes
     * @return the created group
     * @throws GroupAlreadyExistsException if a group with the same name already exists for the given subject type
     * @throws GroupCreationDisallowedException if the group creation is disallowed for the given subject type or subject
     */
    Group createGroup(CreateGroupOptions options) throws GroupAlreadyExistsException, GroupCreationDisallowedException;

    /**
     * Delete a group.
     *
     * @param group the group to delete
     */
    void deleteGroup(Group group);

    /**
     * Assign a parent group to a group.
     *
     * @param group the group to assign a parent to
     * @param parent the parent group to assign
     * @return true if the parent was successfully assigned, false otherwise
     */
    boolean assignGroupParent(Group group, Group parent);

    /**
     * Assign nodes to a group.
     *
     * @param group the group to assign nodes to
     * @param nodes the collection of nodes to assign to the group
     * @return true if the nodes were successfully assigned, false otherwise
     */
    boolean assignGroupNodes(Group group, Collection<Node> nodes);

    /**
     * Get a static group.
     *
     * @param subjectType the subject type to get the group for
     * @param name the name of the group to get
     * @return an Optional containing the group if it exists, or empty if it does not
     */
    Optional<Group> getGroup(SubjectType subjectType, String name);

    /**
     * Get a dynamic group.
     *
     * @param subject the subject to get the group for
     * @param name the name of the group to get
     * @return an Optional containing the group if it exists, or empty if it does not
     */
    Optional<Group> getGroup(Subject subject, String name);

    /**
     * Get all static groups for a subject type.
     *
     * @param subjectType the subject type to get groups for
     * @return a list of static groups for the subject type
     */
    List<Group> getGroups(SubjectType subjectType);

    /**
     * Get all dynamic groups for a subject.
     *
     * @param subject the subject to get groups for
     * @return a list of groups that belong to the subject
     */
    List<Group> getGroups(SubjectLike subject);

    /**
     * Get all group memberships for a subject on a specific subject.
     * The resulting list may contain both static and dynamic groups.
     *
     * @param subject the subject to get group memberships for
     * @param on the subject to check group memberships against
     * @return a list of groups that the subject is a member of on the specified subject
     */
    List<Group> getGroupMemberships(Subject subject, Subject on);
}
