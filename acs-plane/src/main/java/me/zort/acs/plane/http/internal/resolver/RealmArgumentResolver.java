package me.zort.acs.plane.http.internal.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.domain.realm.RealmService;
import me.zort.acs.plane.api.http.mapper.HttpRealmMapper;
import me.zort.acs.plane.http.error.exception.PanelNoDefaultRealmException;
import me.zort.acs.plane.http.internal.service.PathService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

/**
 * This class resolves Realm type of objects in controller methods that don't have any
 * other spring annotations associated.
 * <p>
 * One of the use cases is in the panel where almost every endpoint requires query param
 * "realm" to be present.
 * <p>
 * This resolver will automatically get the realm or throw an error
 * so the underlying layers can redirect the user to the realm creation page.
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class RealmArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpRealmMapper realmMapper;
    private final PathService pathService;
    private final RealmService realmService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Realm.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            // No request???
            return null;
        }

        // A panel resolving logic is only applied to panel paths
        if (pathService.getPathGroup(request.getRequestURI()) == PathService.PathGroup.PANEL) {
            String realmName = webRequest.getParameter("realm");

            Realm realm = null;
            if (realmName != null) {
                realm = realmMapper.toDomain(realmName).or(null).getValue();
            }

            // No realm in the request, try to get the default one
            if (realm == null) {
                realm = getDefaultRealmForLoggedInUser(request);
            }

            if (realm == null) {
                // No default realm
                throw new PanelNoDefaultRealmException();
            }

            return realm;
        }

        // The group this path falls into does not have a realm resolving logic defined.
        return null;
    }

    private @Nullable Realm getDefaultRealmForLoggedInUser(HttpServletRequest request) {
        List<Realm> allRealms = realmService.getAllRealms();
        if (allRealms.isEmpty()) {
            return null;
        } else {
            return allRealms.get(0);
        }
    }
}
