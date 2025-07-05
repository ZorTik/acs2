package me.zort.acs.plane.http.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.domain.realm.RealmService;
import me.zort.acs.plane.api.http.mapper.HttpRealmMapper;
import me.zort.acs.plane.facade.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class HttpRealmMapperImpl implements HttpRealmMapper {
    private final RealmService realmService;

    @Override
    public Result<Realm> toDomain(String http) {
        return realmService.getRealm(http)
                .map(Result::ok).orElse(Result.error(404, "Realm not found"));
    }
}
