package me.zort.acs.plane.http.internal.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.domain.realm.RealmService;
import me.zort.acs.plane.api.domain.user.User;
import me.zort.acs.plane.api.http.mapper.HttpRealmMapper;
import me.zort.acs.plane.http.error.exception.PanelNoDefaultRealmException;
import me.zort.acs.plane.http.internal.service.PathService;
import me.zort.acs.plane.http.security.LoggedInUserDetails;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
            return resolveRealm(webRequest);
        }

        // The group this path falls into does not have a realm resolving logic defined.
        return null;
    }

    /**
     * Resolves the realm from the web request.
     *
     * @param webRequest the web request to resolve the realm from
     * @return the resolved realm
     * @throws PanelNoDefaultRealmException if no realm is specified in the request and no default realm is found for the logged-in user
     */
    public @NotNull Realm resolveRealm(NativeWebRequest webRequest) throws PanelNoDefaultRealmException {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new IllegalStateException("No HttpServletRequest found in the web request.");
        }

        String realmName = webRequest.getParameter("realm");

        Realm realm = null;
        if (realmName != null) {
            realm = realmMapper.toDomain(realmName).or(null).getValue();
        }

        // No realm in the request, try to get the default one
        if (realm == null) {
            User loggedInUser = getLoggedInUser();

            if (loggedInUser != null) {
                realm = realmService.getDefaultRealmForLoggedInUser(loggedInUser).orElse(null);
            }
        }

        if (realm == null) {
            // No default realm
            throw new PanelNoDefaultRealmException();
        }

        return realm;
    }

    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof LoggedInUserDetails loggedInUserDetails) {
            return loggedInUserDetails.getUser();
        }

        return null;
    }
}
