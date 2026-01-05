package me.zort.acs.api.domain.provider;

import me.zort.acs.core.model.Node;
import me.zort.acs.domain.provider.options.NodeOptions;

public interface NodeProvider {

    Node getNode(NodeOptions options);
}
