package me.zort.acs.domain.provider;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.access.validator.AccessRequestValidator;
import me.zort.acs.domain.model.AccessRequest;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectLike;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class AccessRequestProvider {
    private final List<AccessRequestValidator> validators;

    /**
     * Constructs an AccessRequest object with the given parameters.
     *
     * @param from The accessing object
     * @param to The accessed object
     * @param node The node to check applicability for
     * @return An AccessRequest object with the given parameters
     *
     * @throws IllegalArgumentException if the node is not applicable on the accessed object
     */
    public @NotNull AccessRequest createAccessRequest(
            SubjectLike from, SubjectLike to, Node node) throws IllegalArgumentException {
        validate(from, to, node);

        return new AccessRequest(from, to, node);
    }

    private void validate(SubjectLike from, SubjectLike to, Node node) {
        for (AccessRequestValidator validator : validators) {
            String error = validator.validate(from, to, node);

            if (error != null) {
                throw new IllegalArgumentException(error);
            }
        }
    }
}
