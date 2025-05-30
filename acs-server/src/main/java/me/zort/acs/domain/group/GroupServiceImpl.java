package me.zort.acs.domain.group;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.api.domain.operation.OperationExecutor;
import me.zort.acs.api.domain.group.GroupOperationsFactory;
import me.zort.acs.api.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.provider.GroupProvider;
import me.zort.acs.api.domain.service.GroupService;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.id.GroupId;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.GroupOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final DomainModelMapper<Group, GroupEntity> groupMapper;
    private final GroupProvider groupProvider;
    private final GroupOperationsFactory operationsFactory;
    private final OperationExecutor operationExecutor;

    @Override
    public Group createGroup(SubjectType subjectType, String name) {
        return getGroup(subjectType, name).orElseGet(() -> {
            Group group = groupProvider.getGroup(GroupOptions.builder()
                    .subjectType(subjectType)
                    .name(name)
                    .parentGroup(null)
                    .nodes(new HashSet<>()).build());

            group = groupMapper.toDomain(groupRepository.save(groupMapper.toPersistence(group)));

            return group;
        });
    }

    @Override
    public boolean assignGroupParent(Group group, Group parent) {
        return operationExecutor.executeOperation(operationsFactory.assignParent(parent), group);
    }

    @Override
    public boolean assignGroupNodes(Group group, List<Node> nodes) {
        return operationExecutor.executeOperation(operationsFactory.assignNodes(nodes), group);
    }

    @Override
    public Optional<Group> getGroup(SubjectType subjectType, String name) {
        return groupRepository
                .findById(new GroupId(subjectType.getId(), name))
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
