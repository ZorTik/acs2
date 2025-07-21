package me.zort.acs.plane.api.domain.user.exception;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import me.zort.acs.plane.api.domain.user.CreateWithSimpleLoginArgs;

import java.util.Set;

@Getter
public class AccountCreateInvalidFormException extends AccountCreateException {
    private final Set<ConstraintViolation<CreateWithSimpleLoginArgs>> violations;

    public AccountCreateInvalidFormException(Set<ConstraintViolation<CreateWithSimpleLoginArgs>> violations) {
        super("Invalid form data.");
        this.violations = violations;
    }
}
