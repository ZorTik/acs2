package me.zort.acs.plane.api.domain.definitions;

import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;

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
     * Modifies the given definitions model using the specified modification action.
     * <p>
     * The provided {@code modificationAction} is applied to a {@link DefinitionsModification}
     * instance, allowing changes to be made to the supplied {@link DefinitionsModel}.
     * After modification, the {@code modifyCallback} is invoked with the updated model.
     * </p>
     *
     * @param model the {@link DefinitionsModel} to be modified
     * @param modificationAction the action to perform modifications on the model
     * @param modifyCallback a callback to be invoked with the modified model
     * @throws InvalidDefinitionsException if the modification results in an invalid model
     */
    void modifyDefinitions(
            DefinitionsModel model,
            Consumer<DefinitionsModification> modificationAction,
            Consumer<DefinitionsModel> modifyCallback) throws InvalidDefinitionsException;
}
