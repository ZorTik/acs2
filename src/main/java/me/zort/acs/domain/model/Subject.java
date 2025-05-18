package me.zort.acs.domain.model;

import lombok.Getter;
import me.zort.acs.domain.garbage.Disposable;

public class Subject implements Disposable {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Subject)) {
            return false;
        }

        Subject other = (Subject) obj;

        return id.equals(other.id) && type.equals(other.type);
    }
}
