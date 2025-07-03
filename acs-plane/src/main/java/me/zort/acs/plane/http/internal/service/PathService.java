package me.zort.acs.plane.http.internal.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.http.util.PathUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class PathService {
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private final HttpMessageConverter<Object> messageConverter;

    public enum PathGroup {
        PANEL, API, UNKNOWN
    }

    public @NotNull PathGroup getPathGroup(@NotNull String path) {
        if (PATH_MATCHER.match(PathUtils.PANEL_PATH_PATTERN, path)) {
            return PathGroup.PANEL;
        } else if (PATH_MATCHER.match(PathUtils.API_PATH_PATTERN, path)) {
            return PathGroup.API;
        } else {
            return PathGroup.UNKNOWN;
        }
    }
}
