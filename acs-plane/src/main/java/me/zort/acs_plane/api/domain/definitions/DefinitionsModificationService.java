package me.zort.acs_plane.api.domain.definitions;

import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs_plane.api.domain.realm.Realm;

import java.util.function.Consumer;

/**
 * Service interface for modifying definitions within a specific realm.
 * <p>
 * Provides a method to perform modifications on the definitions model
 * using a given modification action and an optional callback.
 * </p>
 */
public interface DefinitionsModificationService {

    /**
     * Modifies the definitions model for the specified realm.
     *
     * @param realm the {@link Realm} in which the definitions are to be modified
     * @param modificationAction the action to perform on the {@link DefinitionsModification}
     * @param modifyCallback a callback to be invoked with the modified {@link DefinitionsModel}
     * @throws InvalidDefinitionsException if the modification results in an invalid model
     */
    void modifyDefinitions(
            Realm realm,
            Consumer<DefinitionsModification> modificationAction,
            Consumer<DefinitionsModel> modifyCallback) throws InvalidDefinitionsException;
}
