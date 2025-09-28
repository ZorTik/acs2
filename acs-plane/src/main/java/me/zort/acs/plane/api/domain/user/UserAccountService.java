package me.zort.acs.plane.api.domain.user;

import me.zort.acs.plane.api.domain.user.exception.AccountCreateException;

public interface UserAccountService {

    User createUserWithSimpleLogin(CreateWithSimpleLoginArgs args) throws AccountCreateException;
}
