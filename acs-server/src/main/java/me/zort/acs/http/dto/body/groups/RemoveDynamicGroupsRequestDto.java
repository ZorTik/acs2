package me.zort.acs.http.dto.body.groups;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RemoveDynamicGroupsRequestDto {
    @NotNull
    @Size(min = 1)
    private List<String> groups;

}
