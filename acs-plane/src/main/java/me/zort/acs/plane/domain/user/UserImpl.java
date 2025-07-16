package me.zort.acs.plane.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.user.User;

@Getter
@RequiredArgsConstructor
public class UserImpl implements User {
    private final long id;
    private final String displayName;

}
