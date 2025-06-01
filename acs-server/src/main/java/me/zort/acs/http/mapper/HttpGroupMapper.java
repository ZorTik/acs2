package me.zort.acs.http.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.service.GroupService;
import me.zort.acs.api.http.exception.HttpExceptionFactory;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.http.dto.model.group.GroupDto;
import me.zort.acs.http.dto.model.node.NodeDto;
import me.zort.acs.http.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class HttpGroupMapper {
    private final HttpNodeMapper nodeMapper;
    private final GroupService groupService;
    private final HttpExceptionFactory exceptionFactory;

    public Group toDomain(SubjectType subjectType, String name) {
        return groupService.getGroup(subjectType, name).orElseThrow(() ->
                exceptionFactory.createException(HttpException.GROUP_NOT_FOUND, null, subjectType, name));
    }

    public GroupDto toHttp(Group group) {
        String parent = group.getParent() != null ? group.getParent().getName() : null;
        List<NodeDto> nodes = group.getNodes()
                .stream()
                .map(nodeMapper::toHttp).toList();

        return new GroupDto(group.getSubjectType().getId(), group.getName(), parent, nodes);
    }
}
