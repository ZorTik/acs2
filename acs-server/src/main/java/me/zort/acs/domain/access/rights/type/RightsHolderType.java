package me.zort.acs.domain.access.rights.type;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.GrantOptions;

import java.util.Optional;

/**
 * Represents a type of rights holder in the access control system.
 *
 * @param <T> the type of RightsHolder
 */
public interface RightsHolderType<T extends RightsHolder> {

    /**
     * Creates a Grant instance from the given rights holder and options.
     *
     * @param holder the rights holder
     * @param options the grant options
     * @return the created Grant
     */
    Grant createGrantFromHolder(T holder, GrantOptions options);

    /**
     * Finds the GrantEntity for the given rights holder and subject IDs.
     *
     * @param holder the rights holder
     * @param accessorId the accessor's subject ID
     * @param accessedId the accessed subject ID
     * @return an Optional containing the GrantEntity if found, otherwise empty
     */
    Optional<GrantEntity> findGrantEntityForHolder(T holder, SubjectId accessorId, SubjectId accessedId);

    /**
     * Checks if the rights holder is present in the specified subject type.
     *
     * @param holder the rights holder
     * @param subjectType the subject type
     * @return true if present, false otherwise
     */
    boolean isPresentInSubjectType(T holder, SubjectType subjectType);

    /**
     * Returns the class type of the rights holder.
     *
     * @return the class of T
     */
    Class<T> getHolderType();
}
