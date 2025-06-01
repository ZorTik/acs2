package me.zort.acs.domain.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
public class SubjectType {
    private final String id;
    private final List<Node> nodes;

    public SubjectType(String id, List<Node> nodes) {
        this.id = id;
        this.nodes = new ArrayList<>(nodes);
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public boolean containsNode(Node node) {
        return nodes.contains(node);
    }

    public Optional<Node> getNodeByValue(String value) {
        return nodes
                .stream()
                .filter(node -> node.getValue().equals(value))
                .findFirst();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SubjectType that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
