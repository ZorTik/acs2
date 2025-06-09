package me.zort.acs.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.domain.util.GroupUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@AllArgsConstructor
public class Group implements RightsHolder {
    private final SubjectType subjectType;
    private final String name;
    private final Set<Node> nodes;

    private Group parent;

    public void setParent(Group parent) throws IllegalArgumentException {
        if (parent != null) {
            if (!parent.getSubjectType().equals(this.subjectType)) {
                throw new IllegalArgumentException("Parent group must have the same subject type as this group.");
            }

            if (parent.equals(this)) {
                throw new IllegalArgumentException("A group cannot be its own parent.");
            }

            if (GroupUtils.detectCircularDependency(parent, Set.of(this))) {
                throw new IllegalArgumentException("Setting this group as a parent would create a circular dependency.");
            }
        }

        this.parent = parent;
    }

    public void addNode(Node node) {
        if (nodes.contains(node)) {
            return;
        }

        nodes.add(node);
    }

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
