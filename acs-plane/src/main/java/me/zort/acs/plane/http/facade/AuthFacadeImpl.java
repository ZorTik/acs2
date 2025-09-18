package me.zort.acs.plane.http.facade;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.plane.api.domain.security.ApiKey;
import me.zort.acs.plane.api.domain.security.ApiKeyService;
import me.zort.acs.plane.api.domain.security.CreateApiKeyParams;
import me.zort.acs.plane.api.domain.user.CreateWithSimpleLoginArgs;
import me.zort.acs.plane.api.domain.user.UserAccountService;
import me.zort.acs.plane.api.domain.user.exception.AccountCreateException;
import me.zort.acs.plane.api.facade.AuthFacade;
import me.zort.acs.plane.http.dto.auth.CreateApiKeyForm;
import me.zort.acs.plane.http.dto.model.ListedApiKey;
import me.zort.acs.plane.http.facade.util.Result;
import me.zort.acs.plane.http.dto.auth.RegisterForm;
import me.zort.acs.plane.http.mapper.HttpApiKeyMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade {
    private final UserAccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final ApiKeyService apiKeyService;
    private final HttpApiKeyMapper apiKeyMapper;

    @Transactional
    @Override
    public Result<Void> register(RegisterForm form) {
        try {
            accountService.createUserWithSimpleLogin(CreateWithSimpleLoginArgs.builder()
                    .username(form.getUsername())
                    .displayName(form.getDisplayName())
                    .password(passwordEncoder.encode(form.getPassword()))
                    .build());

            return Result.ok();
        } catch (AccountCreateException e) {
            return Result.error(400, e.getMessage());
        }
    }

    @Override
    public Result<Void> forceLogin(String username, String password, HttpServletRequest request) {
        try {
            request.login(username, password);

            return Result.ok();
        } catch (ServletException e) {
            log.error("Failed to login user after registration.", e);

            return Result.error(403, "Failed to log in after registration.");
        }
    }

    @Override
    public Result<String> createApiKey(CreateApiKeyForm form) {
        String key = apiKeyService.createApiKey(CreateApiKeyParams.builder()
                .name(form.getName())
                .privileges(form.getClaims())
                .build());

        return Result.ok(key);
    }

    @Override
    public Result<Set<ListedApiKey>> listApiKeys() {
        return Result.ok(apiKeyService.getApiKeys()
                .stream()
                .map(apiKeyMapper::toHttpListed)
                .collect(Collectors.toSet()));
    }
}
