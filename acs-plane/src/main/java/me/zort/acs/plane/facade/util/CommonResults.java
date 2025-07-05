package me.zort.acs.plane.facade.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class CommonResults {

    public static <T> Result<T> realmNotFound(String realmName) {
        return Result.error(404, "Realm '" + realmName + "' not found.");
    }
}
