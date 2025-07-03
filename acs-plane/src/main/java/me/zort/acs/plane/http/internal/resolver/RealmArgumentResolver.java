package me.zort.acs.plane.http.internal.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.http.error.exception.PanelNoDefaultRealmException;
import me.zort.acs.plane.http.internal.service.PathService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class RealmArgumentResolver implements HandlerMethodArgumentResolver {
    private final Converter<String, Realm> realmConverter;
    private final PathService pathService;

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

        String realmName = null;
        switch (pathService.getPathGroup(request.getRequestURI())) {
            case PANEL:
                realmName = webRequest.getParameter("realm");
                if (realmName == null) {
                    realmName = getDefaultRealmNameForLoggedInUser(request);
                }

                if (realmName == null) {
                    throw new PanelNoDefaultRealmException();
                }
                break;
        }

        if (realmName != null) {
            return realmConverter.convert(realmName);
        }

        // The group this path falls into does not have a realm resolving logic defined.
        return null;
    }

    private @Nullable String getDefaultRealmNameForLoggedInUser(HttpServletRequest request) {
        // TODO: Defaultní realm pro přihlášeného uživatele
    }
}
