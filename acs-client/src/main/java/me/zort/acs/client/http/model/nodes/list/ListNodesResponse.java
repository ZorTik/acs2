package me.zort.acs.client.http.model.nodes.list;

import me.zort.acs.client.AcsNodeResolvable;

import java.util.List;

public interface ListNodesResponse {

    List<AcsNodeResolvable> getNodes();
}
