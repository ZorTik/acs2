package me.zort.acs.plane.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.zort.acs.plane.api.domain.security.Role;
import me.zort.acs.plane.api.domain.user.User;

import java.util.UUID;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserImpl implements User {
    private final UUID id;
    private final String displayName;

    @Setter
    private Role role = null;

}
