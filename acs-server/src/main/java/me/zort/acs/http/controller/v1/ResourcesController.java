package me.zort.acs.http.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.AccessService;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.subject.SubjectService;
import me.zort.acs.api.http.exception.HttpException;
import me.zort.acs.api.http.exception.HttpExceptionFactory;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.http.dto.body.BasicResponse;
import me.zort.acs.http.dto.body.subjects.list.ListSubjectsResponseDto;
import me.zort.acs.http.dto.model.subject.SubjectDto;
import me.zort.acs.http.internal.annotation.SubjectRequestParam;
import me.zort.acs.http.mapper.HttpGroupMapper;
import me.zort.acs.http.mapper.HttpNodeMapper;
import me.zort.acs.http.mapper.HttpSubjectMapper;
import me.zort.acs.http.mapper.HttpSubjectTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/v1")
@RestController
public class ResourcesController {
    private final AccessService accessService;
    private final SubjectService subjectService;
    private final HttpSubjectTypeMapper subjectTypeMapper;
    private final HttpGroupMapper groupMapper;
    private final HttpNodeMapper nodeMapper;
    private final HttpSubjectMapper subjectMapper;
    private final HttpExceptionFactory exceptionFactory;

    @PostMapping("/resource/create")
    public SubjectDto create(@RequestBody @Valid SubjectDto dto) {
        Subject subject = subjectMapper.toDomain(dto, false);
        if (subject != null) {
            throw exceptionFactory.createException(HttpException.SUBJECT_ALREADY_EXISTS, null);
        }

        subject = subjectMapper.toDomain(dto, true);

        return subjectMapper.toHttp(subject);
    }

    @PostMapping("/resource/delete")
    public BasicResponse delete(@RequestBody Subject subject) {
        subjectService.deleteSubject(Subject.id(subject));

        return new BasicResponse("Resource deleted successfully");
    }


    @GetMapping("/resources/granted")
    public ListSubjectsResponseDto grantedByHolders(
            @SubjectRequestParam("accessor") Subject accessor,
            @RequestParam String subjectType,
            @RequestParam(required = false, defaultValue = "") String[] groups,
            @RequestParam(required = false, defaultValue = "") String[] nodes, Pageable pageable) {
        if (groups.length == 0 && nodes.length == 0) {
            throw exceptionFactory.createException(HttpException.NODES_GROUPS_NOT_EMPTY, null);
        }

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
