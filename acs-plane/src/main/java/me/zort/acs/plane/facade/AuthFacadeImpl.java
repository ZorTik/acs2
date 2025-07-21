package me.zort.acs.plane.facade;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.user.CreateWithSimpleLoginArgs;
import me.zort.acs.plane.api.domain.user.UserAccountService;
import me.zort.acs.plane.api.domain.user.exception.AccountCreateException;
import me.zort.acs.plane.api.facade.AuthFacade;
import me.zort.acs.plane.facade.util.Result;
import me.zort.acs.plane.http.dto.auth.RegisterForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade {
    private final UserAccountService accountService;
    private final PasswordEncoder passwordEncoder;

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
}
