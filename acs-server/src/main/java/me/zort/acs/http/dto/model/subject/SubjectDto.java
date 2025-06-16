package me.zort.acs.http.dto.model.subject;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.zort.acs.http.dto.SubjectId;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SubjectDto implements SubjectId {
    @NotNull(message = "{validation.subject.group.notnull}")
    private String group;

    @NotNull(message = "{validation.subject.id.notnull}")
    private String id;

}
