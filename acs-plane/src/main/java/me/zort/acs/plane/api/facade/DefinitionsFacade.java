package me.zort.acs.plane.api.facade;

import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.facade.util.Result;

public interface DefinitionsFacade {

    /**
     * Sets the definitions for a given realm.
     *
     * @param realm the realm for which to set the definitions
     * @param definitions the definitions in string format
     * @param formatMimeType the format of the definitions
     * @return a Result indicating success or failure of the operation
     */
    Result<Void> setDefinitions(Realm realm, String definitions, String formatMimeType);

    /**
     * Get serialized definitions format for a given realm.
     *
     * @param realm the realm for which to get the definitions
     * @param formatMimeType the format of the definitions
     * @return a Result containing the definitions in the specified format, or an error if the operation fails
     */
    Result<String> getDefinitions(Realm realm, String formatMimeType);
}
