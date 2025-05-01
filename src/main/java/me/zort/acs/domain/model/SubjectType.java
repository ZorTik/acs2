package me.zort.acs.domain.model;

import lombok.Getter;

public class SubjectType {
    @Getter
    private final String id;

    public SubjectType(String id) {
        this.id = id;
    }
}
