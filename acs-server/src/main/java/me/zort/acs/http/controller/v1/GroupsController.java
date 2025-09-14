package me.zort.acs.http.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.subject.SubjectLike;
import me.zort.acs.api.http.exception.HttpExceptionFactory;
import me.zort.acs.api.http.facade.HttpGroupsFacade;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.http.dto.body.BasicResponse;
import me.zort.acs.http.dto.body.groups.AddDynamicGroupsRequestDto;
import me.zort.acs.http.dto.body.groups.ListGroupsResponseDto;
import me.zort.acs.http.dto.body.groups.RemoveDynamicGroupsRequestDto;
import me.zort.acs.http.dto.model.group.GroupDto;
import me.zort.acs.http.internal.annotation.SubjectRequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Groups", description = "API for managing groups")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/v1/groups")
public class GroupsController {
    private final HttpExceptionFactory exceptionFactory;
    private final HttpGroupsFacade groupsFacade;

    @GetMapping
    @Operation(summary = "Lists groups (filtered)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of groups returned"),
            @ApiResponse(responseCode = "400", description = "Missing or invalid subjectType query parameter")
    })
    public ListGroupsResponseDto listGroups(
            @RequestParam(required = false) String subjectType,
            @SubjectRequestParam(value = "subject", required = false) SubjectLike subject) {
        List<GroupDto> groups;
        if (subjectType != null) {
            groups = groupsFacade.listGroups(subjectType);
        } else if (subject != null) {
            groups = groupsFacade.listGroups(subject);
        } else {
            throw exceptionFactory.createBadQueryException("subjectType", "subject");
        }

        return new ListGroupsResponseDto(groups);
    }

    @PostMapping("/add")
    // TODO: Api spec
    public BasicResponse addDynamicGroups(@RequestBody @Valid AddDynamicGroupsRequestDto body) {
        groupsFacade.addGroups(body.getSubject(), body.getGroups());

        return new BasicResponse("Groups added.");
    }

    @PostMapping("/remove")
    // TODO: Api spec
    public BasicResponse removeDynamicGroups(
            @SubjectRequestParam("subject") Subject subject, @RequestBody @Valid RemoveDynamicGroupsRequestDto body) {
        groupsFacade.removeGroups(subject, body.getGroups());

        return new BasicResponse("Groups removed.");
    }
}
