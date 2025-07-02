package me.zort.acs.plane.domain.realm;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.data.definitions.model.RealmModel;
import me.zort.acs.plane.api.data.realm.RealmRepository;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.domain.realm.RealmPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class RealmPersistenceServiceImpl implements RealmPersistenceService {
    private final RealmRepository realmRepository;
    private final DomainModelMapper<Realm, RealmModel> realmMapper;

    @CachePut(value = "realms", key = "#realm.name", condition = "#realm != null")
    @Override
    public void saveRealm(Realm realm) {
        if (realm == null) {
            throw new IllegalArgumentException("Realm cannot be null");
        }

        realmRepository.save(realmMapper.toPersistence(realm));
    }

    @CacheEvict(value = "realms", key = "#id")
    @Override
    public void deleteRealm(String id) {
        realmRepository.deleteById(id);
    }

    @Cacheable(value = "realms", key = "#id", condition = "#result.present")
    @Override
    public Optional<Realm> getRealm(String id) {
        return realmRepository.findById(id).map(realmMapper::toDomain);
    }

    @Override
    public boolean existsRealm(String id) {
        return realmRepository.existsById(id);
    }
}
