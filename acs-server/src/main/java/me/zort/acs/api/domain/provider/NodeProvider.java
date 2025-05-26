package me.zort.acs.api.domain.provider;

import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.provider.options.NodeOptions;

public interface NodeProvider {

    Node getNode(NodeOptions options);
}
