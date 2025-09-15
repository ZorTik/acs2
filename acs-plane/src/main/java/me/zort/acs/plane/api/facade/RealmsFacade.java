package me.zort.acs.plane.api.facade;

import me.zort.acs.plane.http.facade.util.Result;
import me.zort.acs.plane.http.dto.model.ListedRealm;

import java.util.List;

public interface RealmsFacade {

    Result<Void> createRealm(String name);

    Result<Void> deleteRealm(String name);

    Result<List<ListedRealm>> listRealms();
}
