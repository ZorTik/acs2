package me.zort.acs.plane.api.domain.definitions;

import me.zort.acs.core.domain.ObjectCloner;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;

public interface DefinitionsObjectCloner extends ObjectCloner<DefinitionsModel> {

    /**
     * Copies the contents of one DefinitionsModel into another.
     *
     * @param source the source DefinitionsModel to copy from
     * @param target the target DefinitionsModel to copy into
     */
    void copyInto(DefinitionsModel source, DefinitionsModel target);
}
