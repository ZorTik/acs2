package me.zort.acs.plane.domain.setup;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.setup.SetupService;
import me.zort.acs.plane.api.domain.user.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetupServiceImpl implements SetupService {
    private final UserService userService;

    @Override
    public boolean isSetupComplete() {
        // First account is registered
        // TODO: Zohlednit adminský account
        return userService.getUserCount() > 0;
    }
}
