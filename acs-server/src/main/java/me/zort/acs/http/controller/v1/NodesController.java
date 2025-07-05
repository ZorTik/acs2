package me.zort.acs.http.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.AccessService;
import me.zort.acs.api.domain.subject.SubjectLike;
import me.zort.acs.api.http.exception.HttpExceptionFactory;
import me.zort.acs.http.dto.body.nodes.granted.GrantedNodesResponseDto;
import me.zort.acs.http.dto.body.nodes.list.ListNodesResponseDto;
import me.zort.acs.http.dto.model.node.NodeDto;
import me.zort.acs.http.dto.model.node.NodeWithStateDto;
import me.zort.acs.http.internal.annotation.SubjectRequestParam;
import me.zort.acs.http.mapper.HttpNodeMapper;
import me.zort.acs.http.mapper.HttpSubjectTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Nodes", description = "API for listing nodes and checking granted nodes")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/v1/nodes")
public class NodesController {
    private final HttpNodeMapper nodeMapper;
    private final HttpSubjectTypeMapper subjectTypeMapper;
    private final AccessService accessService;
    private final HttpExceptionFactory exceptionProvider;

    @GetMapping
    @Operation(summary = "Lists nodes (filtered)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of nodes"),
            @ApiResponse(responseCode = "400", description = "Missing or invalid subjectType query parameter")
    })
    public ListNodesResponseDto listNodes(@RequestParam(required = false) String subjectType) {
        List<NodeDto> nodes;
        if (subjectType != null) {
            nodes = subjectTypeMapper.toDomain(subjectType).getNodes()
                    .stream()
                    .map(nodeMapper::toHttp).toList();
        } else {
            throw exceptionProvider.createBadQueryException("subjectType");
        }
        return new ListNodesResponseDto(nodes);
    }

    @GetMapping("/granted")
    @Operation(summary = "Gets nodes granted between accessor and resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Granted nodes returned"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public GrantedNodesResponseDto grantedNodes(
            @SubjectRequestParam("accessor") SubjectLike accessor,
            @SubjectRequestParam("resource") SubjectLike accessed) {
        List<NodeWithStateDto> nodes = accessService.getGrantStatesBetween(accessor, accessed).entrySet()
                .stream()
                .map(entry -> nodeMapper.toHttpWithState(entry.getKey(), entry.getValue()))
                .toList();
        return new GrantedNodesResponseDto(nodes);
    }
}
