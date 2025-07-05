package me.zort.acs.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.api.domain.subject.SubjectLike;

@AllArgsConstructor
public class NullSubject implements SubjectLike {
    private final SubjectType type;
    @Getter
    private final String id;

    @Override
    public SubjectType getSubjectType() {
        return type;
    }

    @Override
    public boolean isNull() {
        return true;
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
