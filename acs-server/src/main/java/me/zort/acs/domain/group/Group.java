package me.zort.acs.domain.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@AllArgsConstructor
public class Group implements RightsHolder {
    private final SubjectType subjectType;
    private final String name;
    private final Set<Node> nodes;

    @Setter
    private Group parent;

    public void addNode(Node node) {
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
    public @Nullable String getIdentifier() {
        return subjectType.getId() + ":" + name;
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
