package me.zort.acs_plane.http.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs_plane.api.domain.realm.Realm;
import me.zort.acs_plane.api.domain.realm.RealmService;
import me.zort.acs_plane.api.http.exception.HttpRealmNotFoundException;
import me.zort.acs_plane.api.http.mapper.HttpRealmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class HttpRealmMapperImpl implements HttpRealmMapper {
    private final RealmService realmService;

    @Override
    public Realm toDomain(String http) {
        return realmService.getRealm(http).orElseThrow(() -> new HttpRealmNotFoundException(http));
    }
}
