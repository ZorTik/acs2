package me.zort.acs.plane.api.domain.user;

import me.zort.acs.plane.api.domain.user.exception.AccountCreateException;

import java.util.UUID;

public interface UserAccountService {

    User createUserWithSimpleLogin(CreateWithSimpleLoginArgs args) throws AccountCreateException;

    void deleteUserWithId(UUID id);
}
