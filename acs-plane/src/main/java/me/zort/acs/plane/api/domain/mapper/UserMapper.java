package me.zort.acs.plane.api.domain.mapper;

import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.api.domain.user.CreateUserArgs;
import me.zort.acs.plane.api.domain.user.User;
import me.zort.acs.plane.data.user.model.UserDocument;

public interface UserMapper extends DomainModelMapper<User, UserDocument> {

    UserDocument toPersistence(CreateUserArgs args) throws IllegalArgumentException;
}
