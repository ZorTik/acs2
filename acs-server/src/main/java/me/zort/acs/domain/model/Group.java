package me.zort.acs.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.RightsHolder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class Group implements RightsHolder {
    private final SubjectType subjectType;
    private final String name;
    private final Group parent;
    private final Set<Node> nodes;

    @Override
    public Set<Node> getGrantedNodes() {
        Set<Node> grantedNodes = new HashSet<>(nodes);

        if (parent != null) {
            grantedNodes.addAll(parent.getGrantedNodes());
        }

        return grantedNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Group group)) return false;
        return Objects.equals(getSubjectType(), group.getSubjectType()) && Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubjectType(), name);
    }

    @Override
    public String toString() {
        return "Group{" +
                "subjectType=" + subjectType +
                ", name='" + name + '\'' +
                '}';
    }
}
