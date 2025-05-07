package me.zort.acs.http.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.service.NodeService;
import me.zort.acs.http.dto.model.node.NodeDto;
import me.zort.acs.http.exception.ACSHttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HttpNodeMapper {
    private final NodeService nodeService;

    public Node toDomain(String node) {
        return nodeService.getNode(node).orElseThrow(() -> new ACSHttpException("No applicable node for " + node, 400));
    }

    public NodeDto toHttp(Node node) {
        String value = node.getValue();
        List<String> subjectTypes = node.getSubjectTypes()
                .stream()
                .map(SubjectType::getId).toList();

        return new NodeDto(value, subjectTypes);
    }

    public String toHttpValue(Node node) {
        return node.getValue();
    }
}
