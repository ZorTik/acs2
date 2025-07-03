package me.zort.acs.plane.http.internal.converter;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.http.mapper.HttpRealmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class RealmConverter implements Converter<String, Realm> {
    private final HttpRealmMapper httpRealmMapper;

    @Override
    public Realm convert(String realmName) {
        return httpRealmMapper.toDomain(realmName).orApiError();
    }
}
