package me.zort.acs.api.domain.group;

import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GroupService {

    Group createGroup(SubjectType subjectType, String name);

    boolean assignGroupParent(Group group, Group parent);

    boolean assignGroupNodes(Group group, Collection<Node> nodes);

    Optional<Group> getGroup(SubjectType subjectType, String name);

    List<Group> getGroups(SubjectType subjectType);

    List<Group> getGroupMemberships(Subject subject, Subject on);
}
