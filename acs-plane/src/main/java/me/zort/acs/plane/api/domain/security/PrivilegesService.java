package me.zort.acs.plane.api.domain.security;

import me.zort.acs.plane.api.domain.user.User;

import java.util.List;

public interface PrivilegesService {

    List<Privilege> getGrantedPrivileges(User user);
}
