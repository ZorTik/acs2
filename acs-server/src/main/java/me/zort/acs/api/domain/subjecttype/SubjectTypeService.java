package me.zort.acs.api.domain.subjecttype;

import me.zort.acs.api.domain.subjecttype.exception.SubjectTypeAlreadyExistsException;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SubjectTypeService {

    /**
     * Creates a new subject type with the given ID and options.
     *
     * @param id the unique identifier for the subject type
     * @param options the options for creating the subject type
     * @return the created SubjectType instance
     * @throws SubjectTypeAlreadyExistsException if a subject type with the given ID already exists
     */
    SubjectType createSubjectType(String id, CreateSubjectTypeOptions options) throws SubjectTypeAlreadyExistsException;

    /**
     * Assigns the given nodes to the specified subject type.
     *
     * @param subjectType the subject type to which nodes will be assigned
     * @param nodes the collection of nodes to assign
     */
    void assignNodes(SubjectType subjectType, Collection<Node> nodes);

    /**
     * Sets whether the specified subject type supports dynamic groups.
     *
     * @param subjectType the subject type to update
     * @param supportsDynamicGroups true if the subject type supports dynamic groups, false otherwise
     */
    void setSupportsDynamicGroups(SubjectType subjectType, boolean supportsDynamicGroups);

    /**
     * Deletes the subject type with the specified ID.
     *
     * @param id the unique identifier of the subject type to delete
     */
    void deleteSubjectType(String id);

    /**
     * Retrieves a subject type by its unique identifier.
     *
     * @param id the unique identifier of the subject type
     * @return an Optional containing the SubjectType if found, or empty if not found
     */
    Optional<SubjectType> getSubjectType(String id);

    /**
     * Retrieves all subject types.
     *
     * @return a list of all subject types
     */
    List<SubjectType> getSubjectTypes();
}
