package me.zort.acs.plane.api.facade;

import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.http.error.HttpError;
import org.jetbrains.annotations.Nullable;

public interface DefinitionsFacade {

    /**
     * Sets the definitions for a given realm.
     *
     * @param realm the realm for which to set the definitions
     * @param definitions the definitions in string format
     * @param format the format of the definitions
     * @return an {@link HttpError} if an error occurs, or {@code null} if the operation is successful
     */
    @Nullable
    HttpError setDefinitions(Realm realm, String definitions, DefinitionsFormat format);
}
