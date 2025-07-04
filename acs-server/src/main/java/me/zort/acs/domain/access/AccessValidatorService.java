package me.zort.acs.domain.access;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.access.validator.AccessRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class AccessValidatorService {
    private final List<AccessRequestValidator> accessRequestValidators;
    private final MessageSource messageSource;

    /**
     * Validates if this access request is valid.
     *
     * @param from The subject checking access
     * @param to The subject to which access is requested
     * @param rightsHolder The rights holder for the access request
     * @throws IllegalArgumentException if the access request is invalid
     */
    public void validate(SubjectLike from, SubjectLike to, RightsHolder rightsHolder) throws IllegalArgumentException {
        for (AccessRequestValidator validator : accessRequestValidators) {
            String error = validator.validate(from, to, rightsHolder);

            if (error != null) {
                try {
                    error = messageSource.getMessage(error, null, Locale.getDefault());
                } catch (NoSuchMessageException ignored) {
                }

                throw new IllegalArgumentException(error);
            }
        }
    }
}
