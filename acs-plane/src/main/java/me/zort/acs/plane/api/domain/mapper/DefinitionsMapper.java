package me.zort.acs.plane.api.domain.mapper;

import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.mapper.PersistenceToDomainMapper;
import me.zort.acs.plane.data.definitions.model.RealmModel;

public interface DefinitionsMapper extends PersistenceToDomainMapper<RealmModel, DefinitionsModel> {

    /**
     * Populates a {@link RealmModel} with the data from a {@link DefinitionsModel}.
     *
     * @param model the {@link DefinitionsModel} to convert
     * @param entity the {@link RealmModel} to populate
     */
    void toPersistence(DefinitionsModel model, RealmModel entity);
}
