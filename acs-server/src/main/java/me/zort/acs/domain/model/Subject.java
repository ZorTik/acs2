package me.zort.acs.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.api.domain.subject.SubjectLike;
import me.zort.acs.api.domain.garbage.disposable.Disposable;
import org.jetbrains.annotations.NotNull;

public class Subject implements SubjectLike, Disposable {
    private final SubjectType type;
    @Getter
    private final String id;

    @Getter
    @AllArgsConstructor
    public static class Id {
        private final String id;
        private final String subjectTypeId;

    }

    public Subject(SubjectType type, String id) {
        this.type = type;
        this.id = id;
    }

    public static @NotNull Subject.Id id(String id, String subjectTypeId) {
        return new Subject.Id(id, subjectTypeId);
    }

    public static @NotNull Subject.Id id(String id, SubjectType subjectType) {
        return new Subject.Id(id, subjectType.getId());
    }

    public static @NotNull Subject.Id id(Subject subject) {
        return new Subject.Id(subject.getId(), subject.getSubjectTypeId());
    }

    public SubjectType getSubjectType() {
        return type;
    }

    public Id getSubjectId() {
        return id(this);
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
