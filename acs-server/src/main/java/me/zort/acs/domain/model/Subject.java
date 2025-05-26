package me.zort.acs.domain.model;

import lombok.Getter;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.api.domain.garbage.disposable.Disposable;

import java.util.List;

public class Subject implements SubjectLike, Disposable {
    private final SubjectType type;
    @Getter
    private final String id;
    private final List<Group> groups;

    public Subject(SubjectType type, String id, List<Group> groups) {
        this.type = type;
        this.id = id;
        this.groups = groups;
    }

    public SubjectType getSubjectType() {
        return type;
    }

    public List<Group> getGroups() {
        return List.copyOf(groups);
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
