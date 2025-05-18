package me.zort.acs.domain.access.validator;

import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectLike;
import org.jetbrains.annotations.Nullable;

/**
 * Validates an AccessRequest object if it's allowed to be even checked for applicability.
 */
public interface AccessRequestValidator {

    @Nullable
    String validate(SubjectLike from, SubjectLike to, Node node);
}
