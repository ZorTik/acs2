package me.zort.acs.api.domain.group;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public interface Group extends RightsHolder {

    UUID getId();

    void setParent(@Nullable Group parent);

    @Nullable
    Group getParent();

    void addNode(Node node);

    void removeNode(Node node);

    boolean containsNode(Node node);

    @Nullable
    SubjectType getSubjectType();

    @Nullable
    Subject getSubject();

    String getName();

    Set<Node> getNodes();
}
