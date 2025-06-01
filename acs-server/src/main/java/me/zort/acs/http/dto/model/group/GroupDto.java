package me.zort.acs.http.dto.model.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.zort.acs.http.dto.model.node.NodeDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupDto {
    private String subjectType;
    private String name;
    private String parent;

    private List<NodeDto> nodes;

}
