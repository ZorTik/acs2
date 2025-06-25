package me.zort.acs.proto.plane;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class PlaneProtocol {

    public static @NotNull String getChannelForRealm(String realmName) {
        return "acs:realm:" + realmName;
    }

    public static Class<PlaneMessage> getImplementingEnum() {
        return PlaneMessage.class;
    }
}
