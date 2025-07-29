package me.zort.acs.http.dto.model.group;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BriefGroupDto {
    @Pattern(regexp = "^\\S+$")
    private String name;

    @NotNull
    private List<String> nodes;

}
