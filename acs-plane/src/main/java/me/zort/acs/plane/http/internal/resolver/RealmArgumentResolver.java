package me.zort.acs.plane.http.internal.resolver;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class RealmArgumentResolver implements HandlerMethodArgumentResolver {
    private final Converter<String, Realm> realmConverter;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Realm.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws MissingServletRequestParameterException {
        String realmName = webRequest.getParameter("realm");
        if (realmName == null) {
            throw new MissingServletRequestParameterException("realm", "String");
        }

        return realmConverter.convert(realmName);
    }
}
