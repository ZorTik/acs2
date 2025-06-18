package me.zort.acs.http.controller.v1;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.AccessService;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.http.dto.body.subjects.list.ListSubjectsResponseDto;
import me.zort.acs.http.dto.model.subject.SubjectDto;
import me.zort.acs.http.internal.annotation.SubjectRequestParam;
import me.zort.acs.http.mapper.HttpGroupMapper;
import me.zort.acs.http.mapper.HttpNodeMapper;
import me.zort.acs.http.mapper.HttpSubjectMapper;
import me.zort.acs.http.mapper.HttpSubjectTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/v1")
@RestController
public class ResourcesController {
    private final AccessService accessService;
    private final HttpSubjectTypeMapper subjectTypeMapper;
    private final HttpGroupMapper groupMapper;
    private final HttpNodeMapper nodeMapper;
    private final HttpSubjectMapper subjectMapper;

    @GetMapping("/resources/granted")
    public ListSubjectsResponseDto grantedByHolders(
            @SubjectRequestParam("accessor") Subject accessor,
            @RequestParam String subjectType,
            @RequestParam String[] groups, @RequestParam String[] nodes, Pageable pageable) {
        SubjectType subjectTypeObj = subjectTypeMapper.toDomain(subjectType);
        // Concatenated list of groups and nodes
        List<RightsHolder> rightsHolders = Stream.concat(
                Arrays.stream(groups).map(group -> groupMapper.toDomain(subjectTypeObj, group)),
                Arrays.stream(nodes).map(nodeMapper::toDomain)).toList();

        List<SubjectDto> subjects = accessService
                .getAccessibleSubjects(accessor, subjectTypeObj, rightsHolders, pageable)
                .map(subjectMapper::toHttp).toList();
        return new ListSubjectsResponseDto(subjects);
    }

    // TODO: Synchronizovat logiku ukládání a načítání definicí z/do databáze
}
