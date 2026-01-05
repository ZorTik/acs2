package me.zort.acs.core.model;

public interface SubjectLike {

    SubjectType getSubjectType();

    String getId();

    boolean isNull();

    default String getSubjectTypeId() {
        return getSubjectType().getId();
    }
}
