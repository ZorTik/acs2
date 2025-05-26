package me.zort.acs.api.domain.service;

import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Optional<Group> getGroup(SubjectType subjectType, String name);

    List<Group> getGroupMemberships(Subject subject, SubjectType on);
}
