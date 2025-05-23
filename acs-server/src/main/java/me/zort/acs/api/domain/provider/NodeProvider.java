package me.zort.acs.api.domain.provider;

import me.zort.acs.domain.model.Node;

public interface NodeProvider {

    Node getNode(String value);
}
