package me.zort.acs.domain.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public final class GroupImpl implements Group {
    private final UUID id;
    private final SubjectType subjectType;
    private final Subject subject;

    private final String name;
    private final Set<Node> nodes;

    @Setter
    private Group parent;

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }

    public boolean containsNode(Node node) {
        return nodes.contains(node);
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
    public String toString() {
        return "GroupImpl{" +
                "subjectType=" + getSubjectType() +
                ", name='" + getName() + '\'' +
                '}';
    }
}
