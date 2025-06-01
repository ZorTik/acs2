package me.zort.acs.http.dto.body.groups;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.zort.acs.http.dto.model.group.GroupDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ListGroupsResponseDto {
    private List<GroupDto> groups;

}
