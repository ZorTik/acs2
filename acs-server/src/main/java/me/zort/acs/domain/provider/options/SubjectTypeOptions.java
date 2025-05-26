package me.zort.acs.domain.provider.options;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.domain.model.Node;

import java.util.List;

@Builder
@Getter
public class SubjectTypeOptions {
    private final String id;
    private final List<Node> nodes;

    public List<Node> getNodes() {
        return List.copyOf(nodes);
    }
}
