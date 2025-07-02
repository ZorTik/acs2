package me.zort.acs.plane.api.domain.mapper;

import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.mapper.PersistenceToDomainMapper;
import me.zort.acs.plane.data.definitions.model.RealmDocument;

public interface DefinitionsMapper extends PersistenceToDomainMapper<RealmDocument, DefinitionsModel> {

    /**
     * Populates a {@link RealmDocument} with the data from a {@link DefinitionsModel}.
     *
     * @param model the {@link DefinitionsModel} to convert
     * @param entity the {@link RealmDocument} to populate
     */
    void toPersistence(DefinitionsModel model, RealmDocument entity);
}
