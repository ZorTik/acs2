package me.zort.acs.plane.api.facade;

import me.zort.acs.plane.facade.util.Result;

public interface RealmsFacade {

    Result<Void> createRealm(String name);
}
