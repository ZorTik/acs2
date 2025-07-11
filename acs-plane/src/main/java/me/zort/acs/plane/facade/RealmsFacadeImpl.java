package me.zort.acs.plane.facade;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.RealmService;
import me.zort.acs.plane.api.domain.realm.exception.RealmAlreadyExistsException;
import me.zort.acs.plane.api.facade.RealmsFacade;
import me.zort.acs.plane.facade.util.Result;
import me.zort.acs.plane.http.dto.model.ListedRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class RealmsFacadeImpl implements RealmsFacade {
    private final RealmService realmService;

    @Override
    public Result<Void> createRealm(String name) {
        try {
            realmService.createRealm(name);

            return Result.ok();
        } catch (RealmAlreadyExistsException e) {
            return Result.error(409, String.format("Realm '%s' already exists", name));
        }
    }

    @Override
    public Result<List<ListedRealm>> listRealms() {
        List<ListedRealm> result = realmService.getAllRealms()
                .stream()
                .map(realm -> new ListedRealm(realm.getName())).toList();

        return Result.ok(result);
    }
}
