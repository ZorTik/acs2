package me.zort.acs.plane.api.domain.definitions;

import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.plane.api.domain.realm.Realm;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Service interface for managing definitions within a specific realm.
 * <p>
 * Provides methods to set and retrieve definitions models for a given realm.
 * </p>
 */
public interface DefinitionsService {

    /**
     * Modifies the definitions model for the specified realm using the provided modification action.
     * <p>
     * The {@code modificationAction} is applied to a {@link DefinitionsModification} instance,
     * allowing changes to the definitions model within the given {@link Realm}.
     * </p>
     *
     * @param realm the {@link Realm} in which the definitions are to be modified
     * @param modificationAction the action to perform modifications on the definitions model
     * @throws InvalidDefinitionsException if the modification results in an invalid model
     */
    void modifyDefinitions(
            Realm realm, Consumer<DefinitionsModification> modificationAction) throws InvalidDefinitionsException;

    /**
     * Sets the definitions model for the specified realm.
     *
     * @param realm the {@link Realm} for which the definitions are to be set
     * @param model the {@link DefinitionsModel} to set, or {@code null} to remove definitions
     * @throws InvalidDefinitionsException if the provided model is invalid
     */
    void setDefinitions(Realm realm, @Nullable DefinitionsModel model) throws InvalidDefinitionsException;

    /**
     * Retrieves the definitions model for the specified realm.
     *
     * @param realm the {@link Realm} for which to retrieve the definitions
     * @return the {@link DefinitionsModel} associated with the given realm
     */
    DefinitionsModel getDefinitions(Realm realm);
}
