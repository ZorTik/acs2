package me.zort.acs.api.domain.service;

import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Group createGroup(SubjectType subjectType, String name);

    boolean assignGroupParent(Group group, Group parent);

    boolean assignGroupNodes(Group group, List<Node> nodes);

    Optional<Group> getGroup(SubjectType subjectType, String name);

    List<Group> getGroups(SubjectType subjectType);

    List<Group> getGroupMemberships(Subject subject, Subject on);
}
