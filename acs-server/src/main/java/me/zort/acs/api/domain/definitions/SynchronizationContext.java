package me.zort.acs.api.domain.definitions;

import me.zort.acs.api.domain.group.Group;
import me.zort.acs.core.model.Node;
import me.zort.acs.core.model.SubjectType;

import java.util.List;

public interface SynchronizationContext {

    void onDefaultGrantDiscovered(SubjectType accessor, SubjectType accessed, List<Node> nodes, List<Group> groups);
}
