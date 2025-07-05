package me.zort.acs.api.domain.subject;

import me.zort.acs.domain.model.Subject;

import java.util.Optional;

/**
 * Service interface for managing {@link Subject} entities.
 * <p>
 * Provides methods for creating, deleting, retrieving, and checking the existence of subjects.
 * </p>
 */
public interface SubjectService {

    /**
     * Creates a new subject based on the provided arguments.
     *
     * @param createArgs the arguments required to create a subject
     * @return the created {@link Subject}
     */
    Subject createSubject(CreateSubjectArgs createArgs);

    /**
     * Deletes the subject with the specified identifier.
     *
     * @param id the identifier of the subject to delete
     */
    void deleteSubject(Subject.Id id);

    /**
     * Retrieves the subject with the specified identifier.
     *
     * @param id the identifier of the subject to retrieve
     * @return an {@link Optional} containing the subject if found, or empty if not found
     */
    Optional<Subject> getSubject(Subject.Id id);

    /**
     * Checks if a subject with the specified identifier exists.
     *
     * @param id the identifier of the subject to check
     * @return {@code true} if the subject exists, {@code false} otherwise
     */
    boolean existsSubject(Subject.Id id);
}
