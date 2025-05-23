package me.zort.acs.client.http.model.nodes.granted;

import me.zort.acs.client.AcsNodeWithStateResolvable;

import java.util.List;

public interface GrantedNodesResponse {

    List<AcsNodeWithStateResolvable> getNodes();

    List<AcsNodeWithStateResolvable> getNodesByState(boolean state);
}
