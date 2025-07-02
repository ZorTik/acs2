package me.zort.acs.plane.http.resolver;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.http.mapper.HttpRealmMapper;
import me.zort.acs.plane.facade.util.CommonResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class RealmArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpRealmMapper httpRealmMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Realm.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String realmName = webRequest.getParameter("realm");
        Optional<Realm> realmOptional = httpRealmMapper.toDomain(realmName);
        if (realmOptional.isEmpty()) {
            throw CommonResults.realmNotFound(realmName).getError();
        } else {
            return realmOptional.get();
        }
    }
}
