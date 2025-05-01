package me.zort.acs.domain.model;

import lombok.Getter;

public class Subject {
    private final SubjectType type;
    @Getter
    private final String id;

    public Subject(SubjectType type, String id) {
        this.type = type;
        this.id = id;
    }

    public SubjectType getSubjectType() {
        return type;
    }

    public String getSubjectTypeId() {
        return type.getId();
    }
}
