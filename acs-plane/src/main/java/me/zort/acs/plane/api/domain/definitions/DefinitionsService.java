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
     * Sets the definitions model for the specified realm.
     *
     * @param realm the {@link Realm} for which the definitions are to be set
     * @param model the {@link DefinitionsModel} to set, or {@code null} to remove definitions
     * @throws InvalidDefinitionsException if the provided model is invalid
     */
    void setDefinitions(Realm realm, @Nullable DefinitionsModel model) throws InvalidDefinitionsException;
}
