package me.zort.acs.domain.access.validator;

import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectLike;
import org.jetbrains.annotations.Nullable;

public interface AccessRequestValidator {

    @Nullable
    String validate(SubjectLike from, SubjectLike to, Node node);
}
