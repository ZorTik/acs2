package me.zort.acs.plane.http.facade;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.plane.api.domain.user.CreateWithSimpleLoginArgs;
import me.zort.acs.plane.api.domain.user.UserAccountService;
import me.zort.acs.plane.api.domain.user.exception.AccountCreateException;
import me.zort.acs.plane.api.facade.AuthFacade;
import me.zort.acs.plane.http.facade.util.Result;
import me.zort.acs.plane.http.dto.auth.RegisterForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade {
    private final UserAccountService accountService;
    private final PasswordEncoder passwordEncoder;

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
}
