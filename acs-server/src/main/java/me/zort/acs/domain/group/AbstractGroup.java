package me.zort.acs.domain.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public abstract class AbstractGroup implements Group {
    private final SubjectType subjectType;
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
}
