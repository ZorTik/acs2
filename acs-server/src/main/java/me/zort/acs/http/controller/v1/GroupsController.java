package me.zort.acs.http.controller.v1;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.service.GroupService;
import me.zort.acs.api.http.exception.HttpExceptionFactory;
import me.zort.acs.http.dto.body.groups.ListGroupsResponseDto;
import me.zort.acs.http.dto.model.group.GroupDto;
import me.zort.acs.http.mapper.HttpGroupMapper;
import me.zort.acs.http.mapper.HttpSubjectTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/v1/groups")
public class GroupsController {
    private final GroupService groupService;
    private final HttpSubjectTypeMapper subjectTypeMapper;
    private final HttpGroupMapper groupMapper;
    private final HttpExceptionFactory exceptionFactory;

    @GetMapping
    public ListGroupsResponseDto listGroups(@RequestParam(required = false) String subjectType) {
        List<GroupDto> groups;
        if (subjectType != null) {
            groups = groupService.getGroups(subjectTypeMapper.toDomain(subjectType))
                    .stream()
                    .map(groupMapper::toHttp).toList();
        } else {
            throw exceptionFactory.createBadQueryException("subjectType");
        }

        return new ListGroupsResponseDto(groups);
    }
}
