package me.zort.acs.plane.domain.mapper;

import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.api.domain.user.User;
import me.zort.acs.plane.data.user.model.UserDocument;
import me.zort.acs.plane.domain.user.UserImpl;
import org.springframework.stereotype.Component;

@Component
public class DomainUserMapper implements DomainModelMapper<User, UserDocument> {

    @Override
    public User toDomain(UserDocument document) {
        return new UserImpl(document.getId(), document.getDisplayName());
    }

    @Override
    public UserDocument toPersistence(User domain) {
        UserDocument document = new UserDocument();
        document.setId(domain.getId());
        document.setDisplayName(domain.getDisplayName());

        return document;
    }
}
