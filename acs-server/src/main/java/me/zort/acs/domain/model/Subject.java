package me.zort.acs.domain.model;

import lombok.Getter;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.api.domain.garbage.disposable.Disposable;

public class Subject implements SubjectLike, Disposable {
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

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SubjectLike other)) {
            return false;
        }

        return id.equals(other.getId()) && type.equals(other.getSubjectType());
    }
}
