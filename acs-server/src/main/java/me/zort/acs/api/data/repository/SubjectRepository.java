package me.zort.acs.api.data.repository;

import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.id.SubjectId;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * Repository interface for managing {@link SubjectEntity} entities.
 * <p>
 * Provides methods for saving, retrieving, checking existence, and deleting subjects
 * by their unique {@link SubjectId}.
 * </p>
 */
@NoRepositoryBean
public interface SubjectRepository {

    /**
     * Saves the given {@link SubjectEntity} to the repository.
     *
     * @param entity the subject entity to save
     * @return the saved subject entity
     */
    SubjectEntity save(SubjectEntity entity);

    /**
     * Retrieves a {@link SubjectEntity} by its unique identifier.
     *
     * @param id the unique identifier of the subject
     * @return an {@link Optional} containing the subject entity if found, or empty if not found
     */
    Optional<SubjectEntity> findById(SubjectId id);

    /**
     * Checks if a {@link SubjectEntity} exists with the given identifier.
     *
     * @param id the unique identifier of the subject
     * @return {@code true} if the subject exists, {@code false} otherwise
     */
    boolean existsById(SubjectId id);

    /**
     * Deletes the {@link SubjectEntity} with the specified identifier.
     *
     * @param id the unique identifier of the subject to delete
     */
    void deleteById(SubjectId id);
}
