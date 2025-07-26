package me.zort.acs.plane.domain.realm;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.data.definitions.model.RealmDocument;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.domain.realm.RealmPersistenceService;
import me.zort.acs.plane.data.definitions.repository.MongoRealmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class RealmPersistenceServiceImpl implements RealmPersistenceService {
    private final MongoRealmRepository realmRepository;
    private final DomainModelMapper<Realm, RealmDocument> realmMapper;

    @Override
    public void saveRealm(Realm realm) {
        if (realm == null) {
            throw new IllegalArgumentException("Realm cannot be null");
        }

        realmRepository.save(realmMapper.toPersistence(realm));
    }

    @Override
    public void deleteRealm(String id) {
        realmRepository.deleteById(id);
    }

    @Override
    public Optional<Realm> getRealm(String id) {
        return realmRepository.findById(id).map(realmMapper::toDomain);
    }

    @Override
    public boolean existsRealm(String id) {
        return realmRepository.existsById(id);
    }

    @Override
    public List<Realm> getAllRealms() {
        return realmRepository.findAll()
                .stream()
                .map(realmMapper::toDomain).toList();
    }
}
