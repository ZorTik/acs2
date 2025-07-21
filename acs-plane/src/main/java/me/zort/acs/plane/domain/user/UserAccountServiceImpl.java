package me.zort.acs.plane.domain.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.credentials.Credentials;
import me.zort.acs.plane.api.domain.credentials.CredentialsService;
import me.zort.acs.plane.api.domain.user.*;
import me.zort.acs.plane.api.domain.user.exception.AccountCreateException;
import me.zort.acs.plane.api.domain.user.exception.AccountCreateInvalidFormException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserAccountServiceImpl implements UserAccountService {
    private final UserService userService;
    private final CredentialsService credentialsService;
    private final Validator validator;

    @Transactional
    @Override
    public User createUserWithSimpleLogin(CreateWithSimpleLoginArgs args) throws AccountCreateException {
        validateCreateArgs(args);

        User user = userService.createUser(CreateUserArgs.builder()
                .displayName(args.getDisplayName())
                .build());
        credentialsService.assignCredentials(user.getId(), args.getUsername(), args.getPassword());
        return user;
    }

    private void validateCreateArgs(CreateWithSimpleLoginArgs args) throws AccountCreateException {
        Optional<? extends Credentials> credentials = credentialsService.getCredentialsByUsername(args.getUsername());
        if (credentials.isPresent()) {
            throw new AccountCreateException("Username already taken.");
        }

        Set<ConstraintViolation<CreateWithSimpleLoginArgs>> violations = validator.validate(args);
        if (!violations.isEmpty()) {
            throw new AccountCreateInvalidFormException(violations);
        }
    }

    @Transactional
    @Override
    public void deleteUserWithId(UUID id) {
        credentialsService.deleteCredentialsByUserId(id);

        userService.deleteUserById(id);
    }
}
