package me.zort.acs.plane.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.data.realm.RealmEntity;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.domain.realm.RealmFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainRealmMapper implements DomainModelMapper<Realm, RealmEntity> {
    private final RealmFactory realmFactory;

    @Override
    public Realm toDomain(RealmEntity persistence) {
        return realmFactory.createRealm(persistence.getId());
    }

    @Override
    public RealmEntity toPersistence(Realm domain) {
        RealmEntity entity = new RealmEntity();
        entity.setId(domain.getName());

        return entity;
    }
}
