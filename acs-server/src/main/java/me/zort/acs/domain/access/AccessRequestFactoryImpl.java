package me.zort.acs.domain.access;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.AccessRequest;
import me.zort.acs.api.domain.access.AccessRequestFactory;
import me.zort.acs.domain.access.validator.AccessRequestValidator;
import me.zort.acs.domain.model.DefaultAccessRequest;
import me.zort.acs.domain.model.Node;
import me.zort.acs.api.domain.model.SubjectLike;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class AccessRequestFactoryImpl implements AccessRequestFactory {
    private final List<AccessRequestValidator> validators;
    private final MessageSource messageSource;

    @Override
    public @NotNull AccessRequest createAccessRequest(
            SubjectLike from, SubjectLike to, Node node) throws IllegalArgumentException {
        validate(from, to, node);

        return new DefaultAccessRequest(from, to, node);
    }

    private void validate(SubjectLike from, SubjectLike to, Node node) {
        for (AccessRequestValidator validator : validators) {
            String error = validator.validate(from, to, node);

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
