package me.zort.acs.domain.group;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.DynamicGroupRepository;
import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.api.domain.group.CreateGroupOptions;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.api.domain.group.exception.GroupAlreadyExistsException;
import me.zort.acs.api.domain.group.exception.GroupCreationDisallowedException;
import me.zort.acs.api.domain.mapper.DomainDynamicGroupIdMapper;
import me.zort.acs.api.domain.mapper.DomainGroupIdMapper;
import me.zort.acs.api.domain.group.GroupOperationsFactory;
import me.zort.acs.api.domain.operation.OperationExecutorProviderService;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.provider.GroupProvider;
import me.zort.acs.api.domain.grant.GrantService;
import me.zort.acs.api.domain.group.GroupService;
import me.zort.acs.data.entity.DynamicGroupEntity;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.GroupOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class GroupServiceImpl implements GroupService {
    private final GrantService grantService;
    private final GroupRepository groupRepository;
    private final DynamicGroupRepository dynamicGroupRepository;
    private final DomainModelMapper<Group, GroupEntity> groupMapper;
    private final DomainGroupIdMapper groupIdMapper;
    private final DomainModelMapper<Group, DynamicGroupEntity> dynamicGroupMapper;
    private final DomainDynamicGroupIdMapper dynamicGroupIdMapper;
    private final DomainModelMapper<Subject.Id, SubjectId> subjectIdMapper;
    private final GroupProvider groupProvider;
    private final OperationExecutorProviderService operationExecutorProviderService;
    private final GroupOperationsFactory operationsFactory;

    @Override
    public Group createGroup(CreateGroupOptions options) throws GroupAlreadyExistsException, GroupCreationDisallowedException {
        Objects.requireNonNull(options, "options cannot be null");
        validateCreateOptions(options);

        // If the subject type where we create this group, does not support dynamic groups and
        // this is a dynamic group creation, we won't allow it.
        if (options.getSubject() != null && !options.getSubject().getSubjectType().isSupportsDynamicGroups()) {
            throw new GroupCreationDisallowedException("Subject type of the subject does not support dynamic groups.");
        }

        Optional<Group> existingGroup;
        if (options.getSubjectType() != null) {
            existingGroup = getGroup(options.getSubjectType(), options.getName());
        } else {
            existingGroup = getGroup(options.getSubject(), options.getName());
        }
        existingGroup.ifPresent(existing -> {
            throw new GroupAlreadyExistsException(existing);
        });

        Group group = groupProvider.getGroup(GroupOptions.builder()
                .subjectType(options.getSubjectType())
                .subject(options.getSubject())
                .name(options.getName())
                .parentGroup(options.getParentGroup())
                .nodes(new HashSet<>(options.getNodes())).build());
        group = groupMapper.toDomain(groupRepository.save(groupMapper.toPersistence(group)));

        return group;
    }

    private void validateCreateOptions(CreateGroupOptions options) {
        if (options.getSubjectType() != null && options.getSubject() != null) {
            throw new IllegalArgumentException("Cannot specify both subject type and subject. Use one or the other.");
        }
    }

    @Override
    public boolean assignGroupParent(Group group, Group parent) {
        return operationExecutorProviderService.getExecutorForGroup(group)
                .executeOperation(operationsFactory.assignParent(parent), group);
    }

    @Override
    public boolean assignGroupNodes(Group group, Collection<Node> nodes) {
        return operationExecutorProviderService.getExecutorForGroup(group)
                .executeOperation(operationsFactory.assignNodes(nodes), group);
    }

    @Override
    public Optional<Group> getGroup(SubjectType subjectType, String name) {
        return groupRepository
                .findById(groupIdMapper.toPersistence(subjectType, name))
                .map(groupMapper::toDomain);
    }

    @Override
    public Optional<Group> getGroup(Subject subject, String name) {
        return dynamicGroupRepository
                .findById(dynamicGroupIdMapper.toPersistence(subject, name))
                .map(dynamicGroupMapper::toDomain);
    }

    @Override
    public List<Group> getGroups(SubjectType subjectType) {
        return groupRepository.findAllBySubjectType_Id(subjectType.getId())
                .stream()
                .map(groupMapper::toDomain).toList();
    }

    @Override
    public List<Group> getGroups(Subject subject) {
        return dynamicGroupRepository.findAllBySubject_Id(subjectIdMapper.toPersistence(Subject.id(subject)))
                .stream()
                .map(dynamicGroupMapper::toDomain).toList();
    }

    @Override
    public List<Group> getGroupMemberships(Subject subject, Subject on) {
        return grantService.getGrants(subject, on)
                .stream()
                .filter(grant -> grant.getRightsHolder() instanceof Group)
                .map(grant -> (Group) grant.getRightsHolder()).toList();
    }
}
