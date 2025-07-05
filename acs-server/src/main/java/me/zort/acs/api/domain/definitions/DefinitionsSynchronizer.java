package me.zort.acs.api.domain.definitions;

import me.zort.acs.core.domain.definitions.model.DefinitionsModel;

/**
 * Interface for synchronizing system definitions with a given model.
 */
public interface DefinitionsSynchronizer {

    /**
     * Synchronizes the system definitions with the specified model and context.
     *
     * @param synchronizeWith the {@link DefinitionsModel} to synchronize with
     * @param context the {@link SynchronizationContext} providing some callbacks
     */
    void synchronizeSystemDefinitions(DefinitionsModel synchronizeWith, SynchronizationContext context);
}
