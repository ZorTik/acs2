package me.zort.acs.http.controller.v1;

import lombok.RequiredArgsConstructor;
import me.zort.acs.http.dto.body.nodes.list.ListNodesResponseDto;
import me.zort.acs.http.dto.model.node.NodeDto;
import me.zort.acs.http.exception.ACSHttpException;
import me.zort.acs.http.mapper.HttpNodeMapper;
import me.zort.acs.http.mapper.HttpSubjectTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/v1/nodes")
public class NodesController {
    private final HttpNodeMapper nodeMapper;
    private final HttpSubjectTypeMapper subjectTypeMapper;

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

    // TODO: List nodes granted for subject on subject
}
