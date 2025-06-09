package me.zort.acs.api.domain.access.rights;

import me.zort.acs.domain.model.Node;

import java.util.Set;

public interface RightsHolder {

    Set<Node> getGrantedNodes();
}
