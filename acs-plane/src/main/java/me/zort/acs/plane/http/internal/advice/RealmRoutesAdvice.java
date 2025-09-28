package me.zort.acs.plane.http.internal.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.http.internal.resolver.RealmArgumentResolver;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.HandlerInterceptor;

@ControllerAdvice
@RequiredArgsConstructor
public class RealmRoutesAdvice implements HandlerInterceptor {
    private final RealmArgumentResolver realmArgumentResolver;

    @ModelAttribute
    public void addRealm(Model model, HttpServletRequest request, NativeWebRequest webRequest) {
        if (request.getRequestURI().matches("^\\/panel\\/realms(?![^\\/]|\\/create).*$")) {
            Realm realm = realmArgumentResolver.resolveRealm(webRequest);

            model.addAttribute("realm", realm);
        }
    }
}
