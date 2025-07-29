package me.zort.acs.http.facade;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.group.CreateGroupOptions;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.api.domain.group.GroupService;
import me.zort.acs.api.domain.subject.SubjectLike;
import me.zort.acs.api.http.exception.HttpException;
import me.zort.acs.api.http.exception.HttpExceptionFactory;
import me.zort.acs.api.http.facade.HttpGroupsFacade;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.http.dto.model.group.BriefGroupDto;
import me.zort.acs.http.dto.model.group.GroupDto;
import me.zort.acs.http.dto.model.subject.SubjectDto;
import me.zort.acs.http.mapper.HttpGroupMapper;
import me.zort.acs.http.mapper.HttpNodeMapper;
import me.zort.acs.http.mapper.HttpSubjectMapper;
import me.zort.acs.http.mapper.HttpSubjectTypeMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HttpGroupsFacadeImpl implements HttpGroupsFacade {
    private final GroupService groupService;
    private final HttpExceptionFactory exceptionFactory;
    private final HttpSubjectTypeMapper subjectTypeMapper;
    private final HttpSubjectMapper subjectMapper;
    private final HttpGroupMapper groupMapper;
    private final HttpNodeMapper nodeMapper;

    @Override
    public List<GroupDto> listGroups(String subjectTypeId) {
        return groupService.getGroups(subjectTypeMapper.toDomain(subjectTypeId))
                .stream()
                .map(groupMapper::toHttp).toList();
    }

    @Override
    public List<GroupDto> listGroups(SubjectLike subject) {
        return groupService.getGroups(subject)
                .stream()
                .map(groupMapper::toHttp).toList();
    }

    @Transactional
    @Override
    public void addGroups(SubjectDto subjectDto, List<BriefGroupDto> groups) {
        if (groups.isEmpty()) {
            // We expect that the validation was done before this method is called
            return;
        }

        // Pre-evaluate nodes to prevent adding someone if there were non-existing ones
        Map<String, List<Node>> nodesByGroup = preEvaluateNodesForGroups(groups);

        Subject subject = subjectMapper.toDomain(subjectDto);
        groups
                .stream()
                .filter(dto -> groupService.getGroup(subject, dto.getName()).isPresent())
                .findFirst().ifPresent(existing -> {
                    throw exceptionFactory.createException(HttpException.GROUP_ALREADY_EXISTS, null, existing.getName());
                });

        groups.forEach(groupDto ->
                groupService.createGroup(CreateGroupOptions.builder()
                        .subject(subject)
                        .name(groupDto.getName())
                        .nodes(nodesByGroup.get(groupDto.getName())).build()));
    }

    private @NotNull Map<String, List<Node>> preEvaluateNodesForGroups(List<BriefGroupDto> groups) {
        return groups
                .stream()
                .collect(Collectors.toMap(BriefGroupDto::getName, group -> group.getNodes()
                        .stream()
                        .map(nodeMapper::toDomain).toList()));
    }

    @Override
    public void removeGroups(Subject subject, List<String> groupNames) {
        List<Group> groupsToRemove = groupNames
                .stream()
                .map(name -> groupService.getGroup(subject, name).orElseThrow(() ->
                        exceptionFactory.createException(HttpException.GROUP_NOT_FOUND, null, name))).toList();

        groupsToRemove.forEach(groupService::deleteGroup);
    }
}
