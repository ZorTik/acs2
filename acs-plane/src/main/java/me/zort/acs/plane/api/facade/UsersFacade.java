package me.zort.acs.plane.api.facade;

import me.zort.acs.plane.http.dto.model.ListedUser;
import me.zort.acs.plane.http.facade.util.Result;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsersFacade {

    /**
     * Lists all users in the system.
     *
     * @param pageable the pagination information
     * @return a Result containing a list of ListedUser objects representing all users
     */
    Result<List<ListedUser>> listUsers(Pageable pageable);
}
