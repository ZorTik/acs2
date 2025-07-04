package me.zort.acs.plane.api.domain.definitions;

import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.domain.realm.exception.RealmNotExistsException;
import org.jetbrains.annotations.Nullable;

/**
 * Service interface for managing definitions within a specific realm.
 * <p>
 * Provides methods to set and retrieve definitions models for a given realm.
 * </p>
 */
public interface DefinitionsService {

    /**
     * Sets the definitions model for the specified realm.
     *
     * @param realmName the name of the realm for which to set the definitions
     * @param model the {@link DefinitionsModel} to set, or {@code null} to remove definitions
     * @throws InvalidDefinitionsException if the provided model is invalid
     * @throws RealmNotExistsException if the specified realm does not exist
     */
    void setDefinitions(String realmName, @Nullable DefinitionsModel model)
            throws InvalidDefinitionsException, RealmNotExistsException;
}
