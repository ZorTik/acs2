package me.zort.acs.plane.http.facade;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.user.UserService;
import me.zort.acs.plane.api.facade.UsersFacade;
import me.zort.acs.plane.http.dto.model.ListedUser;
import me.zort.acs.plane.http.facade.util.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsersFacadeImpl  implements UsersFacade {
    private final UserService userService;

    @Override
    public Result<List<ListedUser>> listUsers(Pageable pageable) {
        // TODO

        return Result.error(500, "Not implemented");
    }
}
