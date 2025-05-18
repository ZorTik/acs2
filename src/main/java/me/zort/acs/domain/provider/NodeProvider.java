package me.zort.acs.domain.provider;

import me.zort.acs.domain.model.Node;
import org.springframework.stereotype.Component;

@Component
public class NodeProvider {

    public Node getNode(String value) {
        return new Node(value);
    }
}
