package me.zort.acs.plane.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.api.domain.definitions.DefinitionsObjectCloner;
import me.zort.acs.plane.api.domain.mapper.DefinitionsMapper;
import me.zort.acs.plane.data.definitions.model.RealmModel;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.domain.realm.RealmFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainRealmMapper implements DomainModelMapper<Realm, RealmModel> {
    private final RealmFactory realmFactory;
    private final DefinitionsObjectCloner definitionsObjectCloner;
    private final DefinitionsMapper definitionsMapper;

    @Override
    public Realm toDomain(RealmModel entity) {
        Realm realm = realmFactory.createRealm(entity.getId());

        DefinitionsModel definitions = definitionsMapper.toDomain(entity);
        definitionsObjectCloner.copyInto(definitions, realm.getDefinitionsModel());

        return realm;
    }

    @Override
    public RealmModel toPersistence(Realm domain) {
        RealmModel entity = new RealmModel();
        entity.setId(domain.getName());

        definitionsMapper.toPersistence(domain.getDefinitionsModel(), entity);

        return entity;
    }
}
