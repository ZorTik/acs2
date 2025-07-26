package me.zort.acs.plane.domain.security;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.security.Privilege;
import me.zort.acs.plane.api.domain.security.PrivilegesService;
import me.zort.acs.plane.api.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivilegesServiceImpl implements PrivilegesService {

    @Override
    public List<Privilege> getGrantedPrivileges(User user) {
        return user.getRole().getPrivileges();
    }
}
