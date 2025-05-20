package me.zort.acs.domain.access.validator;

import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectLike;
import org.jetbrains.annotations.Nullable;

/**
 * Validates an AccessRequest object if it's allowed to be even checked for applicability.
 */
public interface AccessRequestValidator {

    /**
     * Validates the given AccessRequest object.
     *
     * @param from The accessing object
     * @param to The accessed object
     * @param node The node
     * @return Null if the request is valid, otherwise a String describing the error.
     */
    @Nullable
    String validate(SubjectLike from, SubjectLike to, Node node);
}
