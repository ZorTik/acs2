package me.zort.acs.core.domain.access.rights;

import me.zort.acs.core.model.Node;

import java.util.Set;

public interface RightsHolder {

    Set<Node> getGrantedNodes();
}
