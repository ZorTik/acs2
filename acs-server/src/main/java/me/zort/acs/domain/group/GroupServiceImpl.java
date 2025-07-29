package me.zort.acs.domain.group;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.api.domain.group.CreateGroupOptions;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.api.domain.group.exception.GroupAlreadyExistsException;
import me.zort.acs.api.domain.group.exception.GroupCreationDisallowedException;
import me.zort.acs.api.domain.group.GroupOperationsFactory;
import me.zort.acs.api.domain.operation.OperationExecutor;
import me.zort.acs.api.domain.subject.SubjectLike;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.grant.GrantService;
import me.zort.acs.api.domain.group.GroupService;
import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.domain.mapper.DomainGroupMapper;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of the GroupService interface, providing methods to manage groups.
 * <p>
 * Since there are currently two types of groups (static and dynamic),
 * this service handles both types by delegating to the appropriate repositories and mappers.
 *
 * @author ZorTik
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class GroupServiceImpl implements GroupService {
    private final GrantService grantService;
    private final GroupRepository groupRepository;
    private final DomainGroupMapper groupMapper;
    private final DomainModelMapper<Subject, SubjectEntity> subjectMapper;
    private final DomainModelMapper<SubjectType, SubjectTypeEntity> subjectTypeMapper;
    private final OperationExecutor<Group> operationExecutor;
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
        if (existingGroup.isPresent()) {
            throw new GroupAlreadyExistsException(existingGroup.get());
        }

        Group group = groupMapper.toDomain(options);
        // TODO: group init operation
        group = groupMapper.toDomain(groupRepository.save(groupMapper.toPersistence(group)));

        return group;
    }

    private void validateCreateOptions(CreateGroupOptions options) {
        if (options.getSubjectType() != null && options.getSubject() != null) {
            throw new IllegalArgumentException("Cannot specify both subject type and subject. Use one or the other.");
        }
    }

    @Override
    public void deleteGroup(Group group) {
        // TODO: Group cleanup operation

        groupRepository.deleteById(group.getId());
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
        return groupRepository.findBySubjectTypeAndName(subjectTypeMapper.toPersistence(subjectType), name)
                .map(groupMapper::toDomain);
    }

    @Override
    public Optional<Group> getGroup(Subject subject, String name) {
        return groupRepository.findBySubjectAndName(subjectMapper.toPersistence(subject), name)
                .map(groupMapper::toDomain);
    }

    @Override
    public List<Group> getGroups(SubjectType subjectType) {
        return groupRepository.findAllBySubjectType(subjectTypeMapper.toPersistence(subjectType))
                .stream()
                .map(groupMapper::toDomain).toList();
    }

    @Override
    public List<Group> getGroups(SubjectLike subjectLike) {
        List<Group> groups = new ArrayList<>(getGroups(subjectLike.getSubjectType()));

        if (subjectLike instanceof Subject subject) {
            List<Group> dynamicGroups = groupRepository.findAllBySubject(subjectMapper.toPersistence(subject))
                    .stream()
                    .map(groupMapper::toDomain).toList();

            groups.addAll(dynamicGroups);
        }
        return groups;
    }

    @Override
    public List<Group> getGroupMemberships(Subject subject, Subject on) {
        return grantService.getGrants(subject, on)
                .stream()
                .filter(grant -> grant.getRightsHolder() instanceof Group)
                .map(grant -> (Group) grant.getRightsHolder()).toList();
    }
}
