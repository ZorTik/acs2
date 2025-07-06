package me.zort.acs.api.domain.subject;

import me.zort.acs.domain.model.SubjectType;

public interface SubjectLike {

    SubjectType getSubjectType();

    String getId();

    boolean isNull();

    default String getSubjectTypeId() {
        return getSubjectType().getId();
    }
}
