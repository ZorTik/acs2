package me.zort.acs.http.dto.body.groups;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.zort.acs.http.dto.model.group.BriefGroupDto;
import me.zort.acs.http.dto.model.subject.SubjectDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddDynamicGroupsRequestDto {
    @NotNull
    private SubjectDto subject;
    @NotNull
    @Size(min = 1)
    private List<BriefGroupDto> groups;

}
