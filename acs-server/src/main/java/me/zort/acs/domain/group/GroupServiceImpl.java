package me.zort.acs.domain.group;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.api.domain.mapper.DomainGroupIdMapper;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.api.domain.operation.OperationExecutor;
import me.zort.acs.api.domain.group.GroupOperationsFactory;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.provider.GroupProvider;
import me.zort.acs.api.domain.service.GrantService;
import me.zort.acs.api.domain.service.GroupService;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.GroupOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class GroupServiceImpl implements GroupService {
    private final GrantService grantService;
    private final GroupRepository groupRepository;
    private final DomainModelMapper<Group, GroupEntity> groupMapper;
    private final DomainGroupIdMapper groupIdMapper;
    private final GroupProvider groupProvider;
    private final GroupOperationsFactory operationsFactory;
    private final OperationExecutor<Group> operationExecutor;

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
    public boolean assignGroupNodes(Group group, Collection<Node> nodes) {
        return operationExecutor.executeOperation(operationsFactory.assignNodes(nodes), group);
    }

    @Override
    public Optional<Group> getGroup(SubjectType subjectType, String name) {
        return groupRepository
                .findById(groupIdMapper.toPersistence(subjectType, name))
                .map(groupMapper::toDomain);
    }

    @Override
    public List<Group> getGroups(SubjectType subjectType) {
        return groupRepository.findAllBySubjectType_Id(subjectType.getId())
                .stream()
                .map(groupMapper::toDomain).toList();
    }

    @Override
    public List<Group> getGroupMemberships(Subject subject, Subject on) {
        return grantService.getGrants(subject, on)
                .stream()
                .map(Grant::getRightsHolder)
                .filter(holder -> holder instanceof Group)
                .map(holder -> (Group) holder).toList();
    }
}
