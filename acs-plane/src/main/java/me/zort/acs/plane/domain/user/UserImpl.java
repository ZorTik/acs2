package me.zort.acs.plane.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.user.User;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UserImpl implements User {
    private final UUID id;
    private final String displayName;

}
