package me.zort.acs.plane.http.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class PathUtils {
    public static final String PANEL_PATH = "/panel";
    public static final String API_PATH = "/api";

    public static @NotNull String panelPathPattern() {
        return "/panel/**";
    }

    public static @NotNull String apiPathPattern() {
        return "/api/**";
    }
}
