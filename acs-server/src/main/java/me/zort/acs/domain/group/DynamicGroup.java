package me.zort.acs.domain.group;

import lombok.Getter;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;

import java.util.Objects;
import java.util.Set;

@Getter
public class DynamicGroup extends AbstractGroup {
    private final Subject subject;

    public DynamicGroup(Subject subject, String name, Set<Node> nodes, Group parent) {
        super(subject.getSubjectType(), name, nodes, parent);

        this.subject = Objects.requireNonNull(subject, "Subject cannot be null");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Group group)) return false;
        return Objects.equals(getSubjectType(), group.getSubjectType())
                && Objects.equals(getName(), group.getName())
                && Objects.equals(getSubject(), group.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubjectType(), getName(), subject.getId());
    }

    @Override
    public String toString() {
        return "DynamicGroup{" +
                "subjectType=" + getSubjectType() +
                ", subject=" + subject.getId() +
                ", name='" + getName() + '\'' +
                '}';
    }
}
