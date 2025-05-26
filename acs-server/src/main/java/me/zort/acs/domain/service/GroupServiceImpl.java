package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.api.domain.mapper.PersistenceToDomainMapper;
import me.zort.acs.api.domain.service.GroupService;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.id.GroupId;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final PersistenceToDomainMapper<GroupEntity, Group> groupMapper;

    @Override
    public Optional<Group> getGroup(SubjectType subjectType, String name) {
        return groupRepository.findById(new GroupId(subjectType.getId(), name))
                .map(groupMapper::toDomain);
    }

    @Override
    public List<Group> getGroupMemberships(Subject subject, SubjectType on) {
        return subject.getGroups()
                .stream()
                .filter(group -> group.getSubjectType().equals(on))
                .toList();
    }
}
