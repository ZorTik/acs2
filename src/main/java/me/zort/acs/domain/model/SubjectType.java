package me.zort.acs.domain.model;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class SubjectType {
    private final String id;
    private final List<Node> nodes;

    public SubjectType(String id, List<Node> nodes) {
        this.id = id;
        this.nodes = nodes;
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
