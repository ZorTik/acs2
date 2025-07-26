package me.zort.acs.domain.group;

import me.zort.acs.api.domain.group.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;

public final class StaticGroup extends AbstractGroup {

    public StaticGroup(SubjectType subjectType, String name, Set<Node> nodes, Group parent) {
        super(subjectType, name, nodes, parent);
    }

    @Override
    public @Nullable Subject getSubject() {
        // Static groups do not have a subject associated with them.
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Group group)) return false;
        return Objects.equals(getSubjectType(), group.getSubjectType()) && Objects.equals(getName(), group.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubjectType(), getName());
    }

    @Override
    public String toString() {
        return "StaticGroup{" +
                "subjectType=" + getSubjectType() +
                ", name='" + getName() + '\'' +
                '}';
    }
}
