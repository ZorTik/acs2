package me.zort.acs.plane.api.facade;

import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.http.error.HttpError;

public interface DefinitionsFacade {

    /**
     * Sets the definitions for a given realm.
     *
     * @param realm the realm for which to set the definitions
     * @param definitions the definitions in string format
     * @param format the format of the definitions
     */
    void setDefinitions(Realm realm, String definitions, DefinitionsFormat format) throws HttpError;

    String getDefinitions(String realmName, String formatMimeType) throws HttpError;
}
