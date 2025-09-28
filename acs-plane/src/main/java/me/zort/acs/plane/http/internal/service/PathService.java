package me.zort.acs.plane.http.internal.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.http.util.PathUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class PathService {
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    public enum PathGroup {
        PANEL(true), API(false), UNKNOWN(true);

        private final boolean supportsViewModel;

        PathGroup(boolean supportsViewModel) {
            this.supportsViewModel = supportsViewModel;
        }

        /**
         * Returns if this path group supports visual views.
         *
         * @return The state
         */
        public boolean supportsViewModel() {
            return supportsViewModel;
        }
    }

    /**
     * Tries to identify which path group this path belongs to.
     *
     * @param path The path
     * @return The path group, or `PathGroup.UNKNOWN` if not recognized
     */
    public @NotNull PathGroup getPathGroup(@NotNull String path) {
        if (PATH_MATCHER.match(PathUtils.panelPathPattern(), path)) {
            return PathGroup.PANEL;
        } else if (PATH_MATCHER.match(PathUtils.apiPathPattern(), path)) {
            return PathGroup.API;
        } else {
            return PathGroup.UNKNOWN;
        }
    }

    public String getLoginPage() {
        return "/panel/auth/login";
    }

    public String getRegisterPage() {
        return "/panel/auth/register";
    }

    public String getPanelLandingPage() {
        return "/panel";
    }

    public String getPanelPathPattern() {
        return PathUtils.panelPathPattern();
    }

    public String getApiPathPattern() {
        return PathUtils.apiPathPattern();
    }
}
