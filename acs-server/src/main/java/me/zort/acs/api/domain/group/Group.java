package me.zort.acs.api.domain.group;

import me.zort.acs.core.domain.access.rights.RightsHolder;
import me.zort.acs.core.model.Node;
import me.zort.acs.core.model.Subject;
import me.zort.acs.core.model.SubjectType;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface Group extends RightsHolder {

    void setParent(@Nullable Group parent);

    @Nullable
    Group getParent();

    void addNode(Node node);

    void removeNode(Node node);

    boolean containsNode(Node node);

    SubjectType getSubjectType();

    @Nullable
    Subject getSubject();

    String getName();

    Set<Node> getNodes();
}
