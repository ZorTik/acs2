package me.zort.acs.http.controller.v1;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.GrantProvider;
import me.zort.acs.domain.service.GrantService;
import me.zort.acs.domain.service.NodeService;
import me.zort.acs.http.dto.body.nodes.grant.GrantNodesRequestDto;
import me.zort.acs.http.dto.body.nodes.grant.GrantNodesResponseDto;
import me.zort.acs.http.dto.body.nodes.list.ListNodesResponseDto;
import me.zort.acs.http.dto.body.nodes.revoke.RevokeNodesRequestDto;
import me.zort.acs.http.dto.body.nodes.revoke.RevokeNodesResponseDto;
import me.zort.acs.http.dto.model.node.NodeDto;
import me.zort.acs.http.exception.ACSHttpException;
import me.zort.acs.http.mapper.HttpNodeMapper;
import me.zort.acs.http.mapper.HttpSubjectMapper;
import me.zort.acs.http.mapper.HttpSubjectTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/v1/nodes")
public class NodesController {
    private final NodeService nodeService;
    private final GrantService grantService;
    private final GrantProvider grantProvider;
    private final HttpNodeMapper nodeMapper;
    private final HttpSubjectTypeMapper subjectTypeMapper;
    private final HttpSubjectMapper subjectMapper;

    @GetMapping
    public ListNodesResponseDto listNodes(
            @RequestParam(required = false) String subjectType) {
        List<NodeDto> nodes;
        if (subjectType != null) {
            nodes = subjectTypeMapper.toDomain(subjectType).getNodes()
                    .stream()
                    .map(nodeMapper::toHttp).toList();
        } else {
            throw new ACSHttpException("One of [subjectType] request query params required!", 400);
        }

        return new ListNodesResponseDto(nodes);
    }

    @PostMapping("/grant")
    public GrantNodesResponseDto grantNodes(@RequestBody GrantNodesRequestDto body) {
        Subject from = subjectMapper.toDomain(body.getSourceSubject());
        Subject to = subjectMapper.toDomain(body.getTargetSubject());

        Map<String, Boolean> results = body.getNodes()
                .stream()
                .map(nodeMapper::toDomain)
                .collect(Collectors.toMap(Node::getValue, node -> {
                    Grant grant = grantProvider.getGrant(from, to, node);

                    return grantService.addGrant(grant);
                }));

        return new GrantNodesResponseDto(results);
    }

    @PostMapping("/revoke")
    public RevokeNodesResponseDto revokeNodes(@RequestBody RevokeNodesRequestDto body) {
        Subject from = subjectMapper.toDomain(body.getSourceSubject());
        Subject to = subjectMapper.toDomain(body.getTargetSubject());

        Map<String, Boolean> results = body.getNodes()
                .stream()
                .map(nodeMapper::toDomain)
                .collect(Collectors.toMap(Node::getValue, node -> {
                    Grant grant = grantProvider.getGrant(from, to, node);

                    return grantService.removeGrant(grant);
                }));

        return new RevokeNodesResponseDto(results);
    }
}
