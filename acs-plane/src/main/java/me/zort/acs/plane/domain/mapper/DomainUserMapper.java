package me.zort.acs.plane.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.mapper.UserMapper;
import me.zort.acs.plane.api.domain.security.Role;
import me.zort.acs.plane.api.domain.user.CreateUserArgs;
import me.zort.acs.plane.api.domain.user.User;
import me.zort.acs.plane.data.user.model.UserDocument;
import me.zort.acs.plane.domain.user.UserImpl;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DomainUserMapper implements UserMapper {

    @Override
    public User toDomain(UserDocument document) {
        return new UserImpl(document.getId(), document.getDisplayName(), document.getRole());
    }

    @Override
    public UserDocument toPersistence(User domain) {
        UserDocument document = new UserDocument();
        document.setId(domain.getId());
        document.setDisplayName(domain.getDisplayName());
        document.setRole(domain.getRole());

        return document;
    }

    @Override
    public UserDocument toPersistence(CreateUserArgs args) throws IllegalArgumentException {
        UserDocument document = new UserDocument();
        document.setId(UUID.randomUUID());
        document.setDisplayName(args.getDisplayName());
        document.setRole(Role.defaultRole());

        return document;
    }
}
