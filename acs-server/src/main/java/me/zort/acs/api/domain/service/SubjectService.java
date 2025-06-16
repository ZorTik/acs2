package me.zort.acs.api.domain.service;

import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;

import java.util.Optional;

/**
 * Service interface for managing {@link Subject} entities in the system.
 * <p>
 * Provides operations for creating, deleting, retrieving, and checking the existence
 * of subjects based on their {@link SubjectType} and identifier.
 * </p>
 */
public interface SubjectService {

    /**
     * Creates a new {@link Subject} with the specified type and identifier.
     *
     * @param type the type of the subject to create
     * @param id the unique identifier of the subject
     * @return the created subject instance
     */
    Subject createSubject(SubjectType type, String id);

    /**
     * Deletes the specified {@link Subject} from the system.
     *
     * @param subject the subject to delete
     */
    void deleteSubject(Subject subject);

    /**
     * Retrieves a {@link Subject} by its type and identifier.
     *
     * @param type the type of the subject
     * @param id the unique identifier of the subject
     * @return an {@link Optional} containing the subject if found, or empty if not found
     */
    Optional<Subject> getSubject(SubjectType type, String id);

    /**
     * Checks whether a {@link Subject} with the specified type and identifier exists.
     *
     * @param type the type of the subject
     * @param id the unique identifier of the subject
     * @return {@code true} if the subject exists, {@code false} otherwise
     */
    boolean existsSubject(SubjectType type, String id);
}
