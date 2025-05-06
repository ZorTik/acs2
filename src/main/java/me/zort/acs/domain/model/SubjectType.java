package me.zort.acs.domain.model;

import lombok.Getter;

import java.util.Objects;

public class SubjectType {
    @Getter
    private final String id;

    public SubjectType(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SubjectType that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
