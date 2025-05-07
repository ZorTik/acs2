package me.zort.acs.http.controller.v1;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.service.NodeService;
import me.zort.acs.http.dto.body.nodes.list.ListNodesResponse;
import me.zort.acs.http.dto.model.node.NodeDto;
import me.zort.acs.http.exception.ACSHttpException;
import me.zort.acs.http.mapper.HttpNodeMapper;
import me.zort.acs.http.mapper.HttpSubjectTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/v1/nodes")
public class NodesController {
    private final NodeService nodeService;
    private final HttpNodeMapper nodeMapper;
    private final HttpSubjectTypeMapper subjectTypeMapper;

    @GetMapping
    public ResponseEntity<ListNodesResponse> listNodes(
            @RequestParam(required = false) String subjectType) {
        List<NodeDto> nodes;
        if (subjectType != null) {
            SubjectType st = subjectTypeMapper.toDomain(subjectType);

            nodes = nodeService.getNodes(st)
                    .stream()
                    .map(nodeMapper::toHttp).toList();
        } else {
            throw new ACSHttpException("One of [subjectType] request query params required!", 400);
        }

        return ResponseEntity.ok(new ListNodesResponse(nodes));
    }

    @PostMapping("/grant")

    @PostMapping("/revoke")
}
