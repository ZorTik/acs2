package me.zort.acs.http.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.model.Group;
import me.zort.acs.http.dto.model.group.GroupDto;
import me.zort.acs.http.dto.model.node.NodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class HttpGroupMapper {
    private final HttpNodeMapper nodeMapper;

    public GroupDto toHttp(Group group) {
        String parent = group.getParent() != null ? group.getParent().getName() : null;
        List<NodeDto> nodes = group.getNodes()
                .stream()
                .map(nodeMapper::toHttp).toList();

        return new GroupDto(group.getSubjectType().getId(), group.getName(), parent, nodes);
    }
}
