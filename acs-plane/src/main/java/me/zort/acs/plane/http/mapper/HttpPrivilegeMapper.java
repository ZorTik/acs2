package me.zort.acs.plane.http.mapper;

import me.zort.acs.plane.api.domain.security.Privilege;
import me.zort.acs.plane.http.dto.model.ListedPrivilege;
import org.springframework.stereotype.Component;

@Component
public class HttpPrivilegeMapper {

    public ListedPrivilege toHttpListed(Privilege privilege) {
        return new ListedPrivilege(privilege.getAuthority());
    }
}
